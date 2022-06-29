package com.weiran.mission.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.mission.pojo.entity.Goods;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsManagerImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsManager {

}
