package com.weiran.manage.service.admin;

import com.weiran.manage.dto.admin.AdminUserDTO;
import com.weiran.manage.dto.admin.PermissionMenuDTO;
import com.github.pagehelper.PageInfo;
import com.weiran.manage.request.admin.*;

import java.util.List;
import java.util.Optional;


public interface AdminUserService {


    /**
     * 查询管理员
     * @param username
     * @return
     */
    Optional<AdminUserDTO> findByUsername(String username);


    /**
     * 更新管理员信息
     * @param adminUserInfoReq
     * @param username
     * @return
     */
    AdminUserDTO update(AdminUserInfoReq adminUserInfoReq, String username);


    /**
     * 修改用户密码
     * @param adminUserDTO
     * @return
     */
    String updatePass(AdminUserPassReq adminUserDTO);


    /**
     *  查询管理员列表
     * @param page 当前页
     * @param pageSize 每页数量
     * @param search 搜索内容
     * @return adminUsers
     */
    PageInfo<AdminUserDTO> findByAdminUsers(Integer page, Integer pageSize, String search);


    /**
     * 禁用/启用
     * @param id 账号id
     */
    void switchIsBan(Integer id);


    /**
     * 创建管理员
     * @param adminUserReq 管理员
     * @return boolean
     */
    void createAdminUser(AdminUserReq adminUserReq);


    /**
     * 批量删除管理员
     * @param ids 管理员ids
     */
    void deletes(String ids);


    /**
     * 修改管理员信息
     * @param adminUserReq 管理员信息
     */
    void updateAdminUserInfo(AdminUserReq adminUserReq);


    /**
     * 修改管理员权限
     * @param adminUserPermissionDTO 用户权限信息
     */
    void patchAdminUserPermission(AdminUserPermissionDTO adminUserPermissionDTO);


    /**
     * 查询菜单
     * @param username 账号
     * @return 菜单列表
     */
    List<PermissionMenuDTO> findByMenus(String username);


}
