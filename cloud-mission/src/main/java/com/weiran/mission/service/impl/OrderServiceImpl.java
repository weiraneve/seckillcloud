package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;

import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.AuthUtil;
import com.weiran.mission.pojo.entity.Goods;
import com.weiran.mission.pojo.entity.Order;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.mapper.OrderMapper;
import com.weiran.common.pojo.dto.OrderDTO;
import com.weiran.mission.service.OrderService;
import com.weiran.mission.pojo.vo.OrderDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderManager orderManager;
    private final GoodsManager goodsManager;
    private final RedisService redisService;
    private final OrderMapper orderMapper;

    @Override
    public Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request) {
        long userId = redisService.get(UserKey.getById, AuthUtil.getLoginTokenByRequest(request), Long.class);
        return getResultByUserId(userId);
    }

    @Override
    public PageInfo<OrderDTO> findByOrders(Integer page, Integer pageSize, Long id) {
        PageHelper.startPage(page,pageSize);
        List<OrderDTO> orderDTOList;
        if (StringUtils.isEmpty(id)) {
            orderDTOList = orderMapper.findByOrder();
        } else {
            orderDTOList = orderMapper.findOrderById(id);
        }
        return new PageInfo<>(orderDTOList);
    }

    private Result<List<OrderDetailVo>> getResultByUserId(long userId) {
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
