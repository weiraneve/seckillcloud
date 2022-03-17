package com.weiran.manage.service.admin;

import com.weiran.manage.dto.admin.PermissionDTO;
import com.weiran.manage.request.admin.PermissionReq;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface PermissionService {

    /**
     * 分页查询权限
     * @param page 当前页
     * @param pageSize 每页数量
     * @param search 搜索关键字
     * @return
     */
    PageInfo<PermissionDTO> findByPermissions(Integer page, Integer pageSize, String search);


    /**
     * 创建权限
     * @param permissionReq 权限信息
     * @return
     */
    boolean createPermission(PermissionReq permissionReq);


    /**
     * 批量删除权限
     * @param ids ids
     */
    void deletes(String ids);


    /**
     * 修改权限
     * @param permissionReq 权限信息
     * @return
     */
    boolean update(PermissionReq permissionReq);


    /**
     * 查询所有
     * @return
     */
    List<PermissionDTO> findAll();


}
