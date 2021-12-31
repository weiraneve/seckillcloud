package com.weiran.mission.service;

import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.OrderInfo;
import com.weiran.mission.entity.SeckillOrder;
import com.weiran.mission.entity.User;

public interface SeckillOrderService {

    /**
     * 根据用户信息和goodsBo对象插入订单表和秒杀订单表
     */
    OrderInfo insertByUserAndGoodsBo(User user , GoodsBo goodsBo);

    /**
     * 通过OrderId查找SeckillOrder对象
     */
    SeckillOrder selectByOrderId(Long orderId);

    /**
     * 通过UserId和goodsId查找SeckillOrder对象
     */
    SeckillOrder selectByUserIdAndGoodsId(long userId, long goodsId);

}
