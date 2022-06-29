package com.weiran.manage.request;

import lombok.Data;


@Data
public class RoleReq {

    private Integer id;

    private String role;

    private String roleName;

    /**
     * 权限ids
     */
    private String[] permissionIds;

}
