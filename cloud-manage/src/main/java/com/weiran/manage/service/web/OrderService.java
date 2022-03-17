package com.weiran.manage.service.web;

import com.weiran.manage.dto.web.OrderDTO;
import com.github.pagehelper.PageInfo;


public interface OrderService {


    /**
     * 分页查询订单
     */
    PageInfo<OrderDTO> findByOrders(Integer page, Integer pageSize, Long id);


}
