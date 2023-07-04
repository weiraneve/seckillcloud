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
import com.weiran.common.validation.BusinessValidation;
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

    // 显示商品列表
    @Override
    public Result<List<GoodsDetailVo>> getGoodsList() {
        List<GoodsDetailVo> goodsDetailVoList = getGoodsDetailVos();
        Result<List<GoodsDetailVo>> result = new Result<>();
        result.setData(goodsDetailVoList);
        return result;
    }

    private List<GoodsDetailVo> getGoodsDetailVos() {
        List<GoodsDetailVo> goodsDetailVoList = new ArrayList<>();
        // 这里想要遍历缓存中所有商品，但没有更好的办法
        for (long goodsId = 1L; goodsId < 50L; goodsId++) {
            // 在redis缓存中是否存在
            if (!redisService.exists(GoodsKey.goodsKey, String.valueOf(goodsId))) {
                continue;
            }
            Result<GoodsDetailVo> result = getGoodsDetail(goodsId);
            GoodsDetailVo goodsDetailVo = result.getData();
            // 商品信息中是否可用字段
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
        // 判断商品信息是否可用
        if (!goods.getIsUsing()) {
            return null;
        }
        // 用goodsId取出存在Redis中的秒杀商品中的库存值
        return getGoodsDetailVoResult(goodsId, goods);
    }

    private Result<GoodsDetailVo> getGoodsDetailVoResult(long goodsId, Goods goods) {
        // 从redis获取库存
        int stockCount = redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
        long startAt = Timestamp.valueOf(goods.getStartTime()).getTime(); // 秒杀开始时间
        long endAt = Timestamp.valueOf(goods.getEndTime()).getTime(); // 秒杀结束时间
        long now = System.currentTimeMillis(); // 系统当前时间
        int remainSeconds; // 定义剩余时间
        if (now < startAt) { // 秒杀还没开始，倒计时
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) { // 秒杀已经结束
            remainSeconds = -1;
        } else { // 秒杀进行中
            remainSeconds = 0;
        }
        return Result.success(GoodsDetailVo.builder()
                .goods(goods)
                .stockCount(stockCount)
                .remainSeconds(remainSeconds)
                .build());
    }

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
        // 表增加后，在缓存中增加
        addGoodsToCache(goodsDTO);
        return Result.success();
    }

    private void addGoodsToCache(GoodsDTO goodsDTO) {
        redisService.set(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()), goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        redisService.set(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()), goodsDTO.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

    // 删除指定商品
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        if (ObjectUtil.isNotEmpty(goodsDTO.getGoodsImg())) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/",""));
            } catch (MalformedURLException e) {
                log.error(e.toString());
            }
        }
        // 删除对应缓存
        redisService.delete(GoodsKey.goodsKey, String.valueOf(id));
        // 删除库存对应缓存
        redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(id));
        goodsMapper.deleteGoods(id);
        seckillGoodsMapper.deleteSeckillGoods(id);
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
                redisService.delete(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()));
                redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()));
                goodsMapper.deleteGoods(goodsDTO.getId());
                seckillGoodsMapper.deleteSeckillGoods(goodsDTO.getId());
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    // 更新商品
    @Override
    public Result<Object> update(GoodsDTO goodsDTO) {
        goodsMapper.updateGoods(goodsDTO);
        // 更改对应缓存
        addGoodsToCache(goodsDTO);
        // 更改秒杀库
        updateGoodsTOSeckillDatabase(goodsDTO);
        return Result.success();
    }

    private void updateGoodsTOSeckillDatabase(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = POJOConverter.converter(goodsDTO);
        seckillGoodsMapper.updateSeckillGoods(seckillGoodsDTO);
    }

    // 选择单个商品
    @Override
    public GoodsDTO selectById(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        BusinessValidation.isInvalid(ObjectUtil.isNull(goodsDTO), ResponseEnum.RESOURCE_NOT_FOUND);
        return goodsDTO;
    }

    // 修改商品的是否可用
    @Override
    public void updateUsingById(Long id) {
        goodsMapper.updateGoodsUsingById(id);
        GoodsDTO goodsDTO = redisService.get(GoodsKey.goodsKey, String.valueOf(id), GoodsDTO.class);
        goodsDTO.setIsUsing(BooleanUtil.negate(goodsDTO.getIsUsing())); // 布尔值取反
        // 商品缓存中更改
        redisService.set(GoodsKey.goodsKey, String.valueOf(id), goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

}
