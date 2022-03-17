package com.weiran.manage.service.uaa.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.manage.mapper.uaa.SiftMapper;
import com.weiran.manage.dto.uaa.SiftDTO;
import com.weiran.manage.service.uaa.SiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiftServiceImpl implements SiftService {

    private final SiftMapper siftMapper;

    // 分页查询
    @Override
    public PageInfo<SiftDTO> findSift(Integer page, Integer pageSize, Long userId) {
        PageHelper.startPage(page, pageSize);
        List<SiftDTO> siftDTOList;
        if (StringUtils.isEmpty(userId)) {
            siftDTOList = siftMapper.findSift();
        } else {
            // 如果有字段传入，则模糊查询
            siftDTOList = siftMapper.findByUserIdLike(userId);
        }
        return new PageInfo<>(siftDTOList);
    }

}
