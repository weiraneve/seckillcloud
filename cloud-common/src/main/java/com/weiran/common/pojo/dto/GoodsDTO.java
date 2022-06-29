package com.weiran.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "是否启用是必须的")
    private Boolean isUsing;

    // 商品标题
    @NotBlank(message = "商品标题是必须的")
    private String goodsTitle;

    // 商品价格
    @NotNull(message = "商品价格是必须的")
    private BigDecimal goodsPrice;

    // 商品库存
    @NotNull(message = "商品库存是必须的")
    private Integer goodsStock;

    // 秒杀开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @NotNull(message = "秒杀开始时间是必须的")
    private Date startTime;

    // 秒杀结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @NotNull(message = "秒杀结束时间是必须的")
    private Date endTime;

}
