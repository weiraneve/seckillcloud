package com.weiran.manage.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.manage.entity.Goods;
import com.weiran.manage.manager.GoodsManager;
import com.weiran.manage.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsManagerImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsManager {

}
