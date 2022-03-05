package com.weiran.manage.request.admin;

import lombok.Data;


@Data
public class AdminUserPermissionDTO {

    private Integer id;

    private String[] permissionIds;
}
