package com.weiran.uaa.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.uaa.entity.Sift;
import com.weiran.uaa.manager.SiftManager;
import com.weiran.uaa.mapper.SiftMapper;
import org.springframework.stereotype.Service;

/**
 * 秒杀初筛表 服务实现类
 */
@Service
public class SiftManagerImp extends ServiceImpl<SiftMapper, Sift> implements SiftManager {

}
