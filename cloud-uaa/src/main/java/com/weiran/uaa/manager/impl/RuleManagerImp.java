package com.weiran.uaa.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.uaa.entity.Rule;
import com.weiran.uaa.manager.RuleManager;
import com.weiran.uaa.mapper.RuleMapper;
import org.springframework.stereotype.Service;

/**
 * 筛选规则表 服务实现类
 */
@Service
public class RuleManagerImp extends ServiceImpl<RuleMapper, Rule> implements RuleManager {

}
