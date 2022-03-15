package com.weiran.manage.service.uaa.impl;

import com.weiran.manage.entity.uaa.Rule;
import com.weiran.manage.mapper.uaa.RuleMapper;
import com.weiran.manage.service.uaa.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService  {

    private final RuleMapper ruleMapper;

    // 查询rule
    @Override
    public List<Rule> findRule() {
        return ruleMapper.findRule();
    }

    // 修改rule
    @Override
    public boolean update(Rule rule) {
        int flag = ruleMapper.update(rule);
        return flag > 0;
    }

}
