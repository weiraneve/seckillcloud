package com.weiran.manage.service;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.request.RoleReq;

import java.util.List;


public interface RoleService {


    /**
     * 分页查询角色
     */
    PageInfo<RoleDTO> findByRoles(Integer page, Integer pageSize, String search);


    /**
     * 新增角色信息
     */
    void createRole(RoleReq roleReq);

    /**
     * 批量删除
     */
    void deletes(String ids);


    /**
     * 修改角色信息
     */
    void updateRole(RoleReq roleReq);


    /**
     * 查询所有
     */
    List<RoleDTO> findAll();

}
