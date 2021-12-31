package com.weiran.manage.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.manage.entity.SeckillOrder;
import com.weiran.manage.manager.SeckillOrderManager;
import com.weiran.manage.mapper.SeckillOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderManagerImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderManager {

}