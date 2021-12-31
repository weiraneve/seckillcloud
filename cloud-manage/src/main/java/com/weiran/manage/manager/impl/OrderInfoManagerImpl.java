package com.weiran.manage.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.manage.entity.OrderInfo;
import com.weiran.manage.manager.OrderInfoManager;
import com.weiran.manage.mapper.OrderInfoMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoManagerImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoManager {

}