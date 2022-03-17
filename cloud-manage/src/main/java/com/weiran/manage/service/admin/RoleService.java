package com.weiran.manage.service.admin;

import com.weiran.manage.dto.admin.RoleDTO;
import com.weiran.manage.request.admin.RoleReq;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface RoleService {


    /**
     * 分页查询角色
     * @param page 当前页
     * @param pageSize 每页数量
     * @param search 搜索关键字
     * @return 角色
     */
    PageInfo<RoleDTO> findByRoles(Integer page, Integer pageSize, String search);


    /**
     * 新增角色信息
     * @param roleReq 角色信息
     * @return
     */
    boolean createRole(RoleReq roleReq);

    /**
     * 批量删除
     * @param ids 删除的角色ids
     */
    void deletes(String ids);


    /**
     * 修改角色信息
     * @param roleReq 角色信息
     * @return
     */
    boolean updateRole(RoleReq roleReq);


    /**
     * 查询所有
     * @return
     */
    List<RoleDTO> findAll();

}
