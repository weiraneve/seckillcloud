package com.weiran.mission.pojo.vo;

import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.OrderInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单页面对象")
public class OrderDetailVo extends BaseVo {

    @ApiModelProperty(value = "商品业务对象", required = true)
    private GoodsBo goodsBo;

    @ApiModelProperty(value = "订单基础对象", required = true)
    private OrderInfo order;

}
