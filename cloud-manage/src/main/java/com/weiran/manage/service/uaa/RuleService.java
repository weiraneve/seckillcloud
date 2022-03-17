package com.weiran.manage.service.uaa;


import com.weiran.manage.dto.uaa.RuleDTO;

import java.util.List;

public interface RuleService {

    /**
     * 查询rule
     */
    List<RuleDTO> findRule();

    /**
     * 修改rule
     */
    boolean update(RuleDTO ruleDTO);

}
