package com.weiran.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ApiModel(description = "筛选规则表")
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("规定逾期年份")
    private Integer exceedYear;

    @ApiModelProperty("规定逾期次数")
    private Integer exceedCount;

    @ApiModelProperty("规定逾期金额")
    private Integer exceedMoney;

    @ApiModelProperty("规定逾期天数之内还清")
    private Integer exceedDay;

    @ApiModelProperty("限定客户年龄")
    private Integer limitAge;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;

}
