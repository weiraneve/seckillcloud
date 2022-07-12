package com.weiran.manage.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class AdminUserPermissionReq {

    @NotBlank(message = "id是必须的")
    private Integer id;

    @NotBlank(message = "权限id是必须的")
    private String[] permissionIds;
}
