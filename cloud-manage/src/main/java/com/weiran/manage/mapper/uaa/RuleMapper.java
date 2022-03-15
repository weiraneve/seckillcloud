package com.weiran.manage.mapper.uaa;

import com.weiran.manage.entity.uaa.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RuleMapper {

    /**
     * 获得Rule
     */
    List<Rule> findRule();

    /**
     * 更新Rule
     */
    Integer update(@Param("rule") Rule rule);

}
