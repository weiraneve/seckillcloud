package com.weiran.manage.request.admin;

import lombok.Data;


@Data
public class AdminUserReq {

    private Integer id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String role;

    private Integer roleId;

    private Boolean isBan;


}
