package com.weiran.mission.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@TableName("seckill_goods")
@ApiModel(value = "SeckillGoods对象")
public class SeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("秒杀价")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀剩余数量")
    private Integer stockCount;

    @ApiModelProperty("秒杀开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty("秒杀结束时间")
    private LocalDateTime endDate;

    @ApiModelProperty("版本号")
    @Version // 乐观锁字段
    private int version;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @ApiModelProperty("生成时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

}
