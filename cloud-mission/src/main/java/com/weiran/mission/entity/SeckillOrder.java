package com.weiran.mission.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@TableName("seckill_order")
@ApiModel(value = "SeckillOrder对象")
public class SeckillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("生成时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

}
