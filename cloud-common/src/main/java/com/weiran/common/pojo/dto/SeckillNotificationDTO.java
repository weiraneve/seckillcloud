package com.weiran.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SeckillNotificationDTO {

    // 主键
    private Long id;

    // 用户id
    private Long userId;

    // 商品id
    private Long goodsId;

    // 是否已经进行通知
    private Boolean isNotified;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

}
