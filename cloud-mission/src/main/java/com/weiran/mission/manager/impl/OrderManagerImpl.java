package com.weiran.mission.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.mission.mapper.OrderMapper;
import com.weiran.mission.pojo.entity.Order;
import com.weiran.mission.manager.OrderManager;
import org.springframework.stereotype.Service;

@Service
public class OrderManagerImpl extends ServiceImpl<OrderMapper, Order> implements OrderManager {

}
