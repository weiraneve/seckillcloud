package com.weiran.manage.mapper;

import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.request.RoleReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    /**
     * 查询角色
     */
    RoleDTO findById(Integer roleId);

    /**
     * 查询角色
     */
    List<RoleDTO> findByRoles();


    /**
     * 模糊搜索角色
     */
    List<RoleDTO> findByRolesLike(String search);


    /**
     * 新增角色信息
     */
    void createRole(@Param("role") RoleReq roleReq);


    /**
     * 批量删除角色
     */
    void deletesByIds(@Param("roleIds")List<String> roleIds);


    /**
     * 修改角色信息
     */
    void updateRole(@Param("role") RoleReq roleReq);

}
