package com.weiran.manage.service;

import com.weiran.manage.entity.SeckillGoods;
import com.weiran.common.obj.Result;

public interface ManageSeckillGoodsService {

    /**
     * 增加一个秒杀商品
     */
    Result addSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 删除秒杀商品
     */
    Result deleteSeckillGoods(long goodsId);

    /**
     * 更改秒杀商品详情
     */
    Result changeSeckillGoods(long goodsId);

    /**
     * 显示所有秒杀商品列表
     */
    Result getAllSeckillGoods();
}
