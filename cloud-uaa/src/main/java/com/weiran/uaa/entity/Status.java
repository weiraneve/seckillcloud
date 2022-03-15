package com.weiran.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ApiModel(description = "状态表")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("身份证号")
    private String identityCardId;

    @ApiModelProperty("贷款逾期记录(0为正常，1为有)")
    private Integer exceedRecord;

    @ApiModelProperty("客户工作状态(0为正常，1为无业、失业的拒绝情况)")
    private Integer workStatus;

    @ApiModelProperty("客户是否被列入失信名单(0为正常，1为被列入名单)")
    private Integer dishonest;

    @ApiModelProperty("客户年龄(0为正常，1为非正常)")
    private Integer age;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedAt;


}
