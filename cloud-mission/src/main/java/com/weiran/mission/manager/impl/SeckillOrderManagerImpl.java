package com.weiran.mission.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.mission.entity.SeckillOrder;
import com.weiran.mission.manager.SeckillOrderManager;
import com.weiran.mission.mapper.SeckillOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderManagerImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderManager {

}