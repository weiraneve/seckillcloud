package com.weiran.mission.service;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.OrderDTO;
import com.weiran.mission.pojo.vo.OrderDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    /**
     * 返回客户的所有订单数据
     */
    Result<List<OrderDetailVo>> getOrderList(HttpServletRequest request);

    /**
     * 分页查询订单
     */
    PageInfo<OrderDTO> findByOrders(Integer page, Integer pageSize, Long id);

}
