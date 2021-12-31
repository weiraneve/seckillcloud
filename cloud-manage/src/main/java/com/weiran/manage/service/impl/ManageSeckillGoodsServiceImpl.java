package com.weiran.manage.service.impl;

import com.weiran.manage.entity.SeckillGoods;
import com.weiran.manage.manager.SeckillGoodsManager;
import com.weiran.manage.service.ManageSeckillGoodsService;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageSeckillGoodsServiceImpl implements ManageSeckillGoodsService {

    final SeckillGoodsManager seckillGoodsManager;

    // 增加秒杀商品
    @Override
    public Result addSeckillGoods(SeckillGoods seckillGoods) {
        seckillGoodsManager.save(seckillGoods);
        return new Result();
    }

    // 删除秒杀商品
    @Override
    public Result deleteSeckillGoods(long id) {
        return Result.success(CodeMsg.SUCCESS);
    }

    // 更改秒杀商品详情
    @Override
    public Result changeSeckillGoods(long goodsId) {
        return Result.success(CodeMsg.SUCCESS);
    }

    // 显示秒杀所有商品列表
    @Override
    public Result getAllSeckillGoods() {
        return null;
    }
}
