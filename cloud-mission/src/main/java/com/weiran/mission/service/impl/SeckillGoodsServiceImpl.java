package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
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

    // 在库存表增加
    @Override
    public Result addSeckillGoods(SeckillGoods seckillGoods) {
        boolean flag = seckillGoodsManager.save(seckillGoods);
        if (!flag) {
            return new Result(CodeMsg.SERVER_ERROR);
        }
        // 增加对应缓存
        redisService.set(SeckillGoodsKey.seckillCount, "" + seckillGoods.getGoodsId(), seckillGoods.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        return new Result(CodeMsg.SUCCESS);
    }

    // 在库存表删除
    @Override
    public Result deleteSeckillGoods(long goodsId) {
        boolean flag = seckillGoodsManager.remove(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        if (!flag) {
            return new Result(CodeMsg.SERVER_ERROR);
        }
        // 删除对应缓存
        redisService.delete(SeckillGoodsKey.seckillCount, "" + goodsId);
        return new Result(CodeMsg.SUCCESS);
    }

    // 在库存表更改
    @Override
    public Result changeSeckillGoods(SeckillGoods seckillGoods, long goodsId) {
        boolean flag = seckillGoodsManager.update(seckillGoods, Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        if (!flag) {
            return new Result(CodeMsg.SERVER_ERROR);
        }
        // 改对应缓存
        redisService.set(SeckillGoodsKey.seckillCount, "" + goodsId, seckillGoods.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        return new Result(CodeMsg.SUCCESS);
    }

}
