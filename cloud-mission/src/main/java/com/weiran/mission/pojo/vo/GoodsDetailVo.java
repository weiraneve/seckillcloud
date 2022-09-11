package com.weiran.mission.pojo.vo;

import com.weiran.mission.pojo.entity.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel("商品细节页面对象")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailVo extends BaseVo {

    @ApiModelProperty(value = "剩余时间")
    @Builder.Default
    private int remainSeconds = 0;

    @ApiModelProperty(value = "库存数量")
    private int stockCount;

    @ApiModelProperty(value = "商品对象")
    private Goods goods;
}
