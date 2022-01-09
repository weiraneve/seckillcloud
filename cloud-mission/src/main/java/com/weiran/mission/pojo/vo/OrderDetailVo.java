package com.weiran.mission.pojo.vo;


import com.weiran.mission.entity.Goods;
import com.weiran.mission.entity.Order;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单页面对象")
public class OrderDetailVo extends BaseVo {

    @ApiModelProperty(value = "商品信息")
    private Goods goods;

    @ApiModelProperty(value = "订单信息")
    private Order order;

}
