package com.weiran.manage.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@ApiModel(description = "商品表")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图片")
    private String goodsImg;

    @ApiModelProperty("商品标题")
    private String goodsTitle;

    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("商品库存")
    private Integer goodsStock;

    @ApiModelProperty("秒杀开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("秒杀结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("生成时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新商品的时间")
    private LocalDateTime updateTime;

}
