package com.weiran.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "客户贷款领域对象", description = "客户贷款表")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("身份证号")
    private String identityCardId;

    @ApiModelProperty("生成时间")
    private LocalDateTime createDate;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;


}
