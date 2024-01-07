package com.weiran.manage.mapper;

import com.weiran.manage.request.RoleReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolePermissionMapper {


    /**
     * 统计
     */
    Integer countByPermissionIds(@Param("ids") List<String> permissionIds);


    /**
     * 批量新增
     */
    void inserts(@Param("roleReq") RoleReq roleReq);


    /**
     * 批量删除关系
     */
    void deletesByRoleIds(@Param("roleIds") List<String> roleIds);


    /**
     * 查询角色权限ids
     */
    List<Integer> findPermissionIdsByRoleId(Integer roleId);


    /**
     * 根据权限删除关系
     */
    void deletesByPermissionIds(@Param("permissionIds") List<Integer> permissionIds,@Param("roleId") Integer id);

    /**
     * 新增权限
     */
    Integer insertList(@Param("missionIds") List<Integer> missionIds,@Param("roleId") Integer id);


}
