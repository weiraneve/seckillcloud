package com.weiran.manage.service.web.impl;

import com.weiran.manage.mapper.web.OrderMapper;
import com.weiran.manage.dto.web.OrderDTO;
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
}
