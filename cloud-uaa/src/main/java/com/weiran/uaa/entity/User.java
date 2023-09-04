package com.weiran.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(description = "客户表")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("登录手机号")
	private String phone;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("创建时间")
	private LocalDateTime createdAt;

	@ApiModelProperty("更新时间")
	private LocalDateTime updatedAt;

	@ApiModelProperty("最后一次登录时间")
	private Date lastLoginTime;

}
