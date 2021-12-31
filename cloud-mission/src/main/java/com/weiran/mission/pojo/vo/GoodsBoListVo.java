package com.weiran.mission.pojo.vo;

import com.weiran.mission.pojo.bo.GoodsBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("商品业务模型集合对象")
public class GoodsBoListVo extends BaseVo{

    @ApiModelProperty(value = "商品业务对象列表", required = true)
    private List<GoodsBo> goodsBoList;
}
