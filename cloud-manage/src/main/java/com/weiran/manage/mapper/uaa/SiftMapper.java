package com.weiran.manage.mapper.uaa;

import com.weiran.manage.dto.uaa.SiftDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SiftMapper {

    /**
     * 查询全部
     */
    List<SiftDTO> findSift();

    /**
     * 通过userId模糊查询
     */
    List<SiftDTO> findByUserIdLike(Long userId);

}
