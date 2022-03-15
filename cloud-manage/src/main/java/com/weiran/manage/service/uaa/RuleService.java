package com.weiran.manage.service.uaa;


import com.weiran.manage.entity.uaa.Rule;

import java.util.List;

public interface RuleService {

    /**
     * 查询rule
     */
    List<Rule> findRule();

    /**
     * 修改rule
     */
    boolean update(Rule rule);

}
