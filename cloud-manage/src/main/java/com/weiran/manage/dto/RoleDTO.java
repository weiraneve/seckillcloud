package com.weiran.manage.dto;

import lombok.Data;

import java.util.List;


@Data
public class RoleDTO {


    private Integer id;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 拥有权限
     */
    private List<PermissionDTO> permissions;

}
