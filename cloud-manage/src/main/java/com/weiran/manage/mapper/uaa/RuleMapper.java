package com.weiran.manage.mapper.uaa;

import com.weiran.manage.dto.uaa.RuleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface RuleMapper {

    /**
     * 获得Rule
     */
    List<RuleDTO> findRule();

    /**
     * 更新Rule
     */
    Integer update(@Param("ruleDTO") RuleDTO ruleDTO);

}
