package com.weiran.manage.entity.web;

import lombok.Data;

import java.util.Date;


@Data
public class Order {

    // 主键
    private Long id;

    // 用户id
    private String userId;

    // 商品id
    private Long goodsId;

    // 创建时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;

}
