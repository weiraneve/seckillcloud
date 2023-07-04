package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.pojo.entity.SeckillGoods;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.mission.service.SeckillGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    private final SeckillGoodsManager seckillGoodsManager;
    private final RedisService redisService;
    private final SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public int reduceStock(long goodsId) {
        SeckillGoods seckillGoods = seckillGoodsManager.getOne(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        // 多线程并发写的时候，有并发问题，这里只读redis的库存，然后写入库中，避免并发问题。
        reduceStockCount(goodsId, seckillGoods);
        boolean flag = seckillGoodsManager.update(seckillGoods, Wrappers.<SeckillGoods>lambdaUpdate().eq(SeckillGoods::getGoodsId, goodsId));
        return (flag) ? 1 : 0;
    }

    private void reduceStockCount(long goodsId, SeckillGoods seckillGoods) {
        int preStockCount = redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
        seckillGoods.setStockCount(preStockCount);
    }

    @Override
    public PageInfo<SeckillGoodsDTO> findSeckill(Integer page, Integer pageSize, Long goodsId) {
        PageHelper.startPage(page, pageSize);
        List<SeckillGoodsDTO> seckillGoodsDTOList;
        if (StringUtils.isEmpty(goodsId)) {
            seckillGoodsDTOList = seckillGoodsMapper.findSeckill();
        } else {
            seckillGoodsDTOList = seckillGoodsMapper.findByGoodsIdLike(goodsId);
        }
        return new PageInfo<>(seckillGoodsDTOList);
    }
}
