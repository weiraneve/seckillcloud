package com.weiran.manage.service.uaa;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.uaa.SiftDTO;

public interface SiftService {

    /**
     * 分页查询
     */
    PageInfo<SiftDTO> findSift(Integer page, Integer pageSize, Long userId);

}
