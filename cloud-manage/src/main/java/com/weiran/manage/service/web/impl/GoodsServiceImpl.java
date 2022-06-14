package com.weiran.manage.service.web.impl;

import cn.hutool.core.util.BooleanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.GoodsKey;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.manage.dto.web.GoodsDTO;
import com.weiran.manage.dto.web.SeckillGoodsDTO;
import com.weiran.common.exception.CustomizeException;
import com.weiran.manage.mapper.web.GoodsMapper;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.manage.mapper.web.SeckillMapper;
import com.weiran.manage.service.web.GoodsService;
import com.weiran.manage.utils.qiniu.ImageScalaKit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final ImageScalaKit imageScalaKit;
    private final GoodsMapper goodsMapper;
    private final RedisService redisService;
    private final SeckillMapper seckillMapper;

    // 查询所有goods
    @Override
    public PageInfo<GoodsDTO> findGoods(Integer page, Integer pageSize, String goodsName) {
        PageHelper.startPage(page, pageSize);
        List<GoodsDTO> goodsDTOList;
        if (StringUtils.isEmpty(goodsName)) {
            goodsDTOList = goodsMapper.findGoods();
        } else {
            // 如果有字段传入，则模糊查询
            goodsDTOList = goodsMapper.findByGoodsNameLike(goodsName);
        }
        return new PageInfo<>(goodsDTOList);
    }

    // 新增商品
    @Override
    public Result<Object> create(GoodsDTO goodsDTO) {
        try {
            goodsMapper.add(goodsDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResponseEnum.Goods_CREATE_FAIL);
        }
        addGoodsToDatabase(goodsDTO);
        // 表增加后，在缓存中增加
        addGoodsToCache(goodsDTO);
        return Result.success();
    }

    private void addGoodsToCache(GoodsDTO goodsDTO) {
        redisService.set(GoodsKey.goodsKey, "" + goodsDTO.getId(), goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        redisService.set(SeckillGoodsKey.seckillCount, "" + goodsDTO.getId(), goodsDTO.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

    private void addGoodsToDatabase(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
        seckillGoodsDTO.setStockCount(goodsDTO.getGoodsStock());
        seckillGoodsDTO.setGoodsId(goodsDTO.getId());
        seckillMapper.add(seckillGoodsDTO);
    }

    // 删除指定商品
    @Override
    public void delete(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectById(id);
        if (goodsDTO.getGoodsImg() != null) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/",""));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        // 删除对应缓存
        redisService.delete(GoodsKey.goodsKey, "" + id);
        // 删除库存对应缓存
        redisService.delete(SeckillGoodsKey.seckillCount, "" + id);
        goodsMapper.delete(id);
        seckillMapper.delete(id);
    }

    // 批量删除商品
    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> goodsIds = Arrays.asList(split);
        deleteGoods(goodsIds);
    }

    // 删除
    @Async
    void deleteGoods(List<String> goodsIds) {
        List<GoodsDTO> goodsDTOList = goodsMapper.findGoodsByIds(goodsIds);
        for (GoodsDTO goodsDTO : goodsDTOList) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/",""));
                // 删除对应缓存
                redisService.delete(GoodsKey.goodsKey, "" + goodsDTO.getId());
                redisService.delete(SeckillGoodsKey.seckillCount, "" + goodsDTO.getId());
                goodsMapper.delete(goodsDTO.getId());
                seckillMapper.delete(goodsDTO.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 更新商品
    @Override
    public Result<Object> update(GoodsDTO goodsDTO) {
        int row;
        try {
            row = goodsMapper.update(goodsDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResponseEnum.Goods_CREATE_FAIL);
        }
        if (row > 0) {
            // 更改对应缓存
            addGoodsToCache(goodsDTO);
            // 更改秒杀库
            updateGoodsTOSeckillDatabase(goodsDTO);
            return Result.success();
        } else {
            return Result.error(ResponseEnum.Goods_CREATE_FAIL);
        }
    }

    private void updateGoodsTOSeckillDatabase(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = new SeckillGoodsDTO();
        seckillGoodsDTO.setGoodsId(goodsDTO.getId());
        seckillGoodsDTO.setStockCount(goodsDTO.getGoodsStock());
        seckillMapper.update(seckillGoodsDTO);
    }

    // 选择单个商品
    @Override
    public GoodsDTO selectById(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectById(id);
        if (goodsDTO == null) {
            throw new CustomizeException(ResponseEnum.RESOURCE_NOT_FOUND);
        }
        return goodsDTO;
    }

    // 修改商品的是否可用
    @Override
    public void updateUsingById(Long id) {
        goodsMapper.updateUsingById(id);
        GoodsDTO goodsDTO = redisService.get(GoodsKey.goodsKey, "" + id, GoodsDTO.class);
        goodsDTO.setIsUsing(BooleanUtil.negate(goodsDTO.getIsUsing())); // 布尔值取反
        // 商品缓存中更改
        redisService.set(GoodsKey.goodsKey, "" + id, goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }
}
