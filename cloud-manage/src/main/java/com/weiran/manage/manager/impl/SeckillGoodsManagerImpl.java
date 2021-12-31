package com.weiran.manage.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.manage.entity.SeckillGoods;
import com.weiran.manage.manager.SeckillGoodsManager;
import com.weiran.manage.mapper.SeckillGoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillGoodsManagerImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsManager {
}
