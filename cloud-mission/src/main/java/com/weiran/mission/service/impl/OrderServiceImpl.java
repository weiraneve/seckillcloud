package com.weiran.mission.service.impl;

import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;

import com.weiran.mission.entity.Goods;
import com.weiran.mission.entity.Order;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderManager orderManager;
    final GoodsManager goodsManager;

    // 查询订单信息
    public Result<OrderDetailVo> findOrderById(long orderId) {
        Order order = orderManager.getById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        Goods goods = goodsManager.getById(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoods(goods);
        return Result.success(orderDetailVo);
    }
}
