package com.weiran.manage.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class AdminUserReq {

    @NotBlank(message = "参数是必须的")
    private Integer id;

    @NotBlank(message = "名字是必须的")
    private String username;

    @NotBlank(message = "密码是必须的")
    private String password;

    private String name;

    private String phone;

    @NotBlank(message = "角色是必须的")
    private String role;

    private Integer roleId;

    @NotBlank(message = "参数是必须的")
    private Boolean isBan;


}
