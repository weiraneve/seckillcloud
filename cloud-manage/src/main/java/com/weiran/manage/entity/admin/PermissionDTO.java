package com.weiran.manage.entity.admin;

import lombok.Data;


@Data
public class PermissionDTO {

    private Long id;

    /**
     * 权限
     */
    private String permission;

    /**
     * 权限名称
     */
    private String permissionName;
}
