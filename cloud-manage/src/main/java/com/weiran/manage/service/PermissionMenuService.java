package com.weiran.manage.service;

import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;
import com.weiran.manage.request.MenuReq;

import java.util.List;


public interface PermissionMenuService {

    /**
     * 分页查询权限菜单
     */
    List<PermissionMenuDTO> findByRoleMenus(String search);


    /**
     * 查询所有树形结构菜单
     */
    List<TreeRoleMenuDTO> findRoleMenus();


    /**
     * 创建菜单
     */
    void createMenu(MenuReq menuReq);


    /**
     * 删除菜单
     */
    void deleteById(String id);


    /**
     * 修改菜单
     */
    void updateMenu(MenuReq menuReq);

}
