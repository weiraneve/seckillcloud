package com.weiran.mission.service;

import com.weiran.common.obj.Result;
import com.weiran.mission.entity.SeckillGoods;
import org.springframework.web.bind.annotation.RequestParam;

public interface SeckillGoodsService {

    /**
     * 库存减一
     */
    int reduceStock(long goodsId);

    /**
     * 在库存表增加
     */
    Result addSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 在库存表删除
     */
    Result deleteSeckillGoods(@RequestParam("goodsId") long goodsId);


    /**
     * 在库存表更改
     */
    Result changeSeckillGoods(SeckillGoods seckillGoods, @RequestParam("goodsId") long goodsId);

}
