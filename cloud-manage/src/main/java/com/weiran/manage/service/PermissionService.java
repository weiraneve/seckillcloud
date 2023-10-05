package com.weiran.manage.service;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.manage.dto.PermissionDTO;
import com.weiran.manage.request.PermissionReq;

import java.util.List;


public interface PermissionService {

    /**
     * 分页查询权限
     */
    PageInfo<PermissionDTO> findByPermissions(Integer page, Integer pageSize, String search);


    /**
     * 创建权限
     */
    Result<Object> createPermission(PermissionReq permissionReq);


    /**
     * 批量删除权限
     */
    void deletes(String ids);


    /**
     * 修改权限
     */
    Result<Object> update(PermissionReq permissionReq);


    /**
     * 查询所有
     */
    List<PermissionDTO> findAll();


}
