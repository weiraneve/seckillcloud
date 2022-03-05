package com.weiran.manage.request.admin;

import lombok.Data;


@Data
public class PermissionReq {

    private Integer id;

    /**
     * 权限
     */
    private String permission;

    /**
     * 权限名称
     */
    private String permissionName;


}
