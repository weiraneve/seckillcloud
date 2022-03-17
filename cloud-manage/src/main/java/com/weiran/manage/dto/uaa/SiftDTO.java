package com.weiran.manage.dto.uaa;

import lombok.Data;

import java.util.Date;

@Data
public class SiftDTO {

    // 主键
    private Long id;

    // 用户id
    private Long userId;

    // 初筛是否通过
    private boolean siftPass;

    // 身份证号
    private String identityCardId;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;

}
