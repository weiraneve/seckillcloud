package com.weiran.manage.request;

import lombok.Data;

@Data
public class AdminUserPasswordUpdateReq {

    private String username;

    private String oldPassword;

    private String newPassword;
}
