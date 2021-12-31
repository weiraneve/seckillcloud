package com.weiran.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "秒杀筛选领域对象", description = "秒杀初筛表")
public class Sift implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("初筛是否通过")
    private Integer siftPass;

    @ApiModelProperty("身份证号")
    private String identityCardId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("生成时间")
    private LocalDateTime createDate;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateDate;


}
