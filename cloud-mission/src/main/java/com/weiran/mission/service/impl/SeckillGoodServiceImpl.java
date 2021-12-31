package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.service.SeckillGoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeckillGoodServiceImpl implements SeckillGoodService {

    final SeckillGoodsManager seckillGoodsManager;

    // 库存减一
    @Override
    public int reduceStock(long goodsId) {
        SeckillGoods seckillGoods = seckillGoodsManager.getOne(Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId));
        int preStockCount = seckillGoods.getStockCount();
        if (preStockCount <= 0) {
            return 0;
        }
        seckillGoods.setStockCount(preStockCount - 1);
        boolean flag = seckillGoodsManager.update(seckillGoods, Wrappers.<SeckillGoods>lambdaUpdate().eq(SeckillGoods::getGoodsId, goodsId));
        return (flag) ? 1 : 0;
    }
}
