package com.weiran.mission.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "Goods对象")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品标题")
    private String goodsTitle;

    @ApiModelProperty("商品图片")
    private String goodsImg;

    @ApiModelProperty("商品介绍详情")
    private String goodsDetail;

    @ApiModelProperty("商品单价")
    private BigDecimal goodsPrice;

    @ApiModelProperty("商品库存，-1表示没有限制")
    private Integer goodsStock;

    @ApiModelProperty("生成时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @ApiModelProperty("更新商品的时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
