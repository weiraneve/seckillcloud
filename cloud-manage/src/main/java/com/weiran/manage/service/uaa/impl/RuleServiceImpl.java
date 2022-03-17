package com.weiran.manage.service.uaa.impl;

import com.weiran.manage.dto.uaa.RuleDTO;
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
    public List<RuleDTO> findRule() {
        return ruleMapper.findRule();
    }

    // 修改rule
    @Override
    public boolean update(RuleDTO ruleDTO) {
        int flag = ruleMapper.update(ruleDTO);
        return flag > 0;
    }

}
