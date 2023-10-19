package com.weiran.manage.service;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.AdminUserDTO;
import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.request.*;

import java.util.List;
import java.util.Optional;


public interface AdminUserService {


    /**
     * 查询管理员
     */
    Optional<AdminUserDTO> findByUsername(String username);


    /**
     * 更新管理员信息
     */
    AdminUserDTO update(AdminUserInfoReq adminUserInfoReq, String username);


    /**
     * 修改用户密码
     */
    String updatePass(AdminUserPasswordUpdateReq adminUserPasswordUpdateReq);


    /**
     *  查询管理员列表
     */
    PageInfo<AdminUserDTO> findByAdminUsers(Integer page, Integer pageSize, String search);


    /**
     * 禁用/启用
     */
    void switchIsBan(Integer id);


    /**
     * 创建管理员
     */
    void createAdminUser(AdminUserReq adminUserReq);


    /**
     * 批量删除管理员
     */
    void deletes(String ids);


    /**
     * 修改管理员信息
     */
    void updateAdminUserInfo(AdminUserReq adminUserReq);


    /**
     * 修改管理员权限
     */
    void patchAdminUserPermission(AdminUserPermissionReq adminUserPermissionReq);


    /**
     * 查询菜单
     */
    List<PermissionMenuDTO> findByMenus(String username);


}
