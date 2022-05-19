package com.weiran.manage.dto.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsDTO {

    // 主键
    private Long id;

    // 商品名称
    @NotBlank(message = "商品名称是必须的")
    private String goodsName;

    // 商品图片链接
    private String goodsImg;

    // 是否启用
    @NotBlank(message = "是否启用是必须的")
    private Boolean isUsing;

    // 商品标题
    @NotBlank(message = "商品标题是必须的")
    private String goodsTitle;

    // 商品价格
    @NotBlank(message = "商品价格是必须的")
    private BigDecimal goodsPrice;

    // 商品库存
    @NotBlank(message = "商品库存是必须的")
    private Integer goodsStock;

    // 秒杀开始时间
    @NotBlank(message = "秒杀开始时间是必须的")
    private Date startTime;

    // 秒杀结束时间
    @NotBlank(message = "秒杀结束时间是必须的")
    private Date endTime;

}