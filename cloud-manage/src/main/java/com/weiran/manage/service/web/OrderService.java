package com.weiran.manage.service.web;

import com.weiran.manage.entity.web.Order;
import com.github.pagehelper.PageInfo;


public interface OrderService {


    /**
     * 分页查询订单
     */
    PageInfo<Order> findByOrders(Integer page, Integer pageSize, Long id);


}
