package com.weiran.manage.dto;

import lombok.Data;


@Data
public class RolePermissionDTO {

    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permissionId;

    /**
     * 角色信息
     */
    private RoleDTO role;

    /**
     * 权限信息
     */
    private PermissionDTO permission;

}
