package com.weiran.mission.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@TableName("order_info")
@ApiModel(value = "OrderInfo对象")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("收货地址id")
    private Long addrId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty("支付通道：1 PC、2 Android、3 ios")
    private Integer orderChannel;

    @ApiModelProperty("订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，‘5 已完成")
    private Integer status;

    @ApiModelProperty("订单生成的时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

}
