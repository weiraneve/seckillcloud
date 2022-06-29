package com.weiran.mission.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.mission.pojo.entity.SeckillGoods;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillGoodsManagerImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsManager {
}
