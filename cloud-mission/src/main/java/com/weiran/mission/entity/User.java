package com.weiran.mission.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "User对象")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登陆手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("存放信息头部")
    private String head;

    @ApiModelProperty("登陆记录")
    private Integer loginCount;

    @ApiModelProperty("生成时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)  //反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)  //序列化
    private LocalDateTime createDate;

    @ApiModelProperty("最后一次登陆时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)  //反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)  //序列化
    private LocalDateTime lastLoginDate;

}
