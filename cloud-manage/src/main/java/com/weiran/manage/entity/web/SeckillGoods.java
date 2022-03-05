package com.weiran.manage.entity.web;

import lombok.Data;

@Data
public class SeckillGoods {

    // 主键
    private Long id;

    // 商品id
    private Long goodsId;

    // 剩余库存
    private int stockCount;

}
