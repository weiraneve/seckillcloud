package com.weiran.manage.dto.web;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsDTO {

    // 主键
    private Long id;

    // 商品名称
    private String goodsName;

    // 商品图片链接
    private String goodsImg;

    // 是否启用
    private Boolean isUsing;

    // 商品标题
    private String goodsTitle;

    // 商品价格
    private BigDecimal goodsPrice;

    // 商品库存
    private Integer goodsStock;

    // 秒杀开始时间
    private Date startTime;

    // 秒杀结束时间
    private Date endTime;

}