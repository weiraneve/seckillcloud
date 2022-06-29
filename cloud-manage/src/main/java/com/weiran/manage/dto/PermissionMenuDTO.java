package com.weiran.manage.dto;

import lombok.Data;

import java.util.List;


@Data
public class PermissionMenuDTO {

    private Integer id;

    /**
     * 一级还是二级菜单
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限Id
     */
    private Integer permissionId;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 组件页面名
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     *  图标 设置该路由的图标
     */
    private String icon;

    /**
     * 权限
     */
    private PermissionDTO permission;

    public List<PermissionMenuDTO> children = null;


}
