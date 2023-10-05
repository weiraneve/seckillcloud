package com.weiran.mission.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.redis.key.GoodsKey;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.validation.CustomValidation;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.mapper.GoodsMapper;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import com.weiran.mission.pojo.entity.Goods;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.utils.POJOConverter;
import com.weiran.mission.utils.qiniu.ImageScalaKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final ImageScalaKit imageScalaKit;
    private final GoodsManager goodsManager;
    private final GoodsMapper goodsMapper;
    private final SeckillGoodsMapper seckillGoodsMapper;
    private final RedisService redisService;

    /**
     * 系统初始化，把商品信息加载到Redis缓存中。后续客户访问都从缓存中读取。
     */
    @PostConstruct
    public void initGoodsInfo() {
        List<Goods> goodsList = goodsManager.list();
        for (Goods goods : goodsList) {
            redisService.set(GoodsKey.goodsKey, goods.getId().toString(), goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        }
    }

    @Override
    public Result<List<GoodsDetailVo>> getGoodsList() {
        List<GoodsDetailVo> goodsDetailVoList = getGoodsDetailVos();
        Result<List<GoodsDetailVo>> result = new Result<>();
        result.setData(goodsDetailVoList);
        return result;
    }

    private List<GoodsDetailVo> getGoodsDetailVos() {
        List<GoodsDetailVo> goodsDetailVoList = new ArrayList<>();
        for (long goodsId = 1L; goodsId < 50L; goodsId++) {
            if (!redisService.exists(GoodsKey.goodsKey, String.valueOf(goodsId))) {
                continue;
            }
            Result<GoodsDetailVo> result = getGoodsDetail(goodsId);
            GoodsDetailVo goodsDetailVo = result.getData();
            if (goodsDetailVo.getGoods().getIsUsing()) {
                goodsDetailVoList.add(goodsDetailVo);
            }
        }
        return goodsDetailVoList;
    }

    // 显示商品细节。剩余时间等于0，正在秒杀中；剩余时间大于0，还没有开始秒杀；小于0，已经结束秒杀。
    @Override
    public Result<GoodsDetailVo> getGoodsDetail(long goodsId) {
        // 从Redis中读取商品数据，这样多次访问都从缓存中取，减少数据库的压力
        Goods goods = redisService.get(GoodsKey.goodsKey, String.valueOf(goodsId), Goods.class);
        if (!goods.getIsUsing()) {
            return null;
        }
        return getGoodsDetailVoResult(goodsId, goods);
    }

    private Result<GoodsDetailVo> getGoodsDetailVoResult(long goodsId, Goods goods) {
        int stockCount = redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
        long startTime = Timestamp.valueOf(goods.getStartTime()).getTime();
        long endTime = Timestamp.valueOf(goods.getEndTime()).getTime();
        long now = System.currentTimeMillis();
        int remainSeconds;
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            remainSeconds = -1;
        } else {
            remainSeconds = 0;
        }
        return Result.success(GoodsDetailVo.builder()
                .goods(goods)
                .stockCount(stockCount)
                .remainSeconds(remainSeconds)
                .build());
    }

    @Override
    public PageInfo<GoodsDTO> findGoods(Integer page, Integer pageSize, String goodsName) {
        PageHelper.startPage(page, pageSize);
        List<GoodsDTO> goodsDTOList;
        if (StringUtils.isEmpty(goodsName)) {
            goodsDTOList = goodsMapper.findGoods();
        } else {
            goodsDTOList = goodsMapper.findByGoodsNameLike(goodsName);
        }
        return new PageInfo<>(goodsDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> create(GoodsDTO goodsDTO) {
        try {
            goodsMapper.addGoods(goodsDTO);
            SeckillGoodsDTO seckillGoodsDTO = POJOConverter.converter(goodsDTO);
            seckillGoodsMapper.addSeckillGoods(seckillGoodsDTO);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.fail(ResponseEnum.GOODS_CREATE_FAIL);
        }
        addGoodsToCache(goodsDTO);
        return Result.success();
    }

    private void addGoodsToCache(GoodsDTO goodsDTO) {
        redisService.set(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()), goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        redisService.set(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()), goodsDTO.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        if (ObjectUtil.isNotEmpty(goodsDTO.getGoodsImg())) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/", ""));
            } catch (MalformedURLException e) {
                log.error(e.toString());
            }
        }
        redisService.delete(GoodsKey.goodsKey, String.valueOf(id));
        redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(id));
        goodsMapper.deleteGoods(id);
        seckillGoodsMapper.deleteSeckillGoods(id);
    }

    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> goodsIds = Arrays.asList(split);
        deleteGoods(goodsIds);
    }

    @Async
    void deleteGoods(List<String> goodsIds) {
        List<GoodsDTO> goodsDTOList = goodsMapper.findGoodsByIds(goodsIds);
        for (GoodsDTO goodsDTO : goodsDTOList) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/", ""));
                redisService.delete(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()));
                redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()));
                goodsMapper.deleteGoods(goodsDTO.getId());
                seckillGoodsMapper.deleteSeckillGoods(goodsDTO.getId());
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    @Override
    public Result<Object> update(GoodsDTO goodsDTO) {
        goodsMapper.updateGoods(goodsDTO);
        addGoodsToCache(goodsDTO);
        updateGoodsForSeckillDatabase(goodsDTO);
        return Result.success();
    }

    private void updateGoodsForSeckillDatabase(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = POJOConverter.converter(goodsDTO);
        seckillGoodsMapper.updateSeckillGoods(seckillGoodsDTO);
    }

    @Override
    public GoodsDTO selectById(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        CustomValidation.isInvalid(ObjectUtil.isNull(goodsDTO), ResponseEnum.RESOURCE_NOT_FOUND);
        return goodsDTO;
    }

    @Override
    public void updateUsingById(Long id) {
        goodsMapper.updateGoodsUsingById(id);
        GoodsDTO goodsDTO = redisService.get(GoodsKey.goodsKey, String.valueOf(id), GoodsDTO.class);
        goodsDTO.setIsUsing(BooleanUtil.negate(goodsDTO.getIsUsing()));
        redisService.set(GoodsKey.goodsKey, String.valueOf(id), goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

}
