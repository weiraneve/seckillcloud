package com.weiran.manage.request;

import lombok.Data;


@Data
public class MenuReq {

    private Integer id;

    /**
     * 菜单id
     */
    private Integer menuId;

    private Integer level;

    /**
     * 组件页面
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
     * 权限id
     */
    private Integer permissionId;

    /**
     * 组件页面排序
     */
    private Integer sort;

}
