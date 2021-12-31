package com.weiran.mission.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.mission.mapper.OrderInfoMapper;
import com.weiran.mission.entity.OrderInfo;
import com.weiran.mission.manager.OrderInfoManager;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoManagerImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoManager {

}