package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.service.SeckillGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    final SeckillGoodsManager seckillGoodsManager;
    final RedisService redisService;

    // 库存表库存减一
    @Override
    public int reduceStock(long goodsId) {
        SeckillGoods seckillGoods = seckillGoodsManager.getOne(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        // 多线程并发写的时候，有并发问题，这里只读redis的库存，然后写入库中，避免并发问题。
        int preStockCount = redisService.get(SeckillGoodsKey.seckillCount, "" + goodsId, Integer.class);
        seckillGoods.setStockCount(preStockCount);
        boolean flag = seckillGoodsManager.update(seckillGoods, Wrappers.<SeckillGoods>lambdaUpdate().eq(SeckillGoods::getGoodsId, goodsId));
        return (flag) ? 1 : 0;
    }

}
