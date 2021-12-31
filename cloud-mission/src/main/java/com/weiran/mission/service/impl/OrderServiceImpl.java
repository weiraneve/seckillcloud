package com.weiran.mission.service.impl;

import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.OrderInfo;
import com.weiran.mission.entity.SeckillOrder;
import com.weiran.mission.manager.OrderInfoManager;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.service.SeckillOrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderInfoManager orderInfoManager;
    final RedisService redisService;
    final SeckillOrderService seckillOrderService;
    final GoodsService goodsService;

    // 查询订单信息
    @Override
    public Result<OrderDetailVo> info(long orderId) {
        SeckillOrder seckillOrder = seckillOrderService.selectByOrderId(orderId);
        if (seckillOrder == null) {
            return null;
        }
        OrderInfo orderInfo = orderInfoManager.getById(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsBo goodsBo = goodsService.getGoodsBoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(orderInfo);
        orderDetailVo.setGoodsBo(goodsBo);
        return Result.success(orderDetailVo);
    }
}
