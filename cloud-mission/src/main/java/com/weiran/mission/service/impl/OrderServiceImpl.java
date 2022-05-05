package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.obj.Result;

import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.entity.Goods;
import com.weiran.mission.entity.Order;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderManager orderManager;
    final GoodsManager goodsManager;
    final RedisService redisService;

    // 返回客户的所有订单数据
    @Override
    public Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1];
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        List<OrderDetailVo> orderDetailVoList = new ArrayList<>();
        List<Order> orderList = orderManager.list(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, userId));
        for (Order order : orderList) {
            Goods goods = goodsManager.getById(order.getGoodsId());
            orderDetailVoList.add(OrderDetailVo.builder()
                    .orderId(order.getId())
                    .goodsId(order.getGoodsId())
                    .goodsName(goods.getGoodsName())
                    .createdAt(order.getCreatedAt())
                    .build());
        }
        return Result.success(orderDetailVoList);
    }
}
