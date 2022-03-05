package com.weiran.manage.service.web.impl;

import com.weiran.manage.mapper.web.OrderMapper;
import com.weiran.manage.entity.web.Order;
import com.weiran.manage.service.web.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public PageInfo<Order> findByOrders(Integer page, Integer pageSize, Long id) {
        PageHelper.startPage(page,pageSize);
        List<Order> orderList;
        if (StringUtils.isEmpty(id)) {
            orderList = orderMapper.findByOrder();
        } else {
            orderList = orderMapper.findOrderById(id);
        }
        return new PageInfo<>(orderList);
    }
}
