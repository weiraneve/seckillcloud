package com.weiran.manage.mapper.admin;

import com.weiran.manage.request.admin.RoleReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface RolePermissionMapper {


    /**
     * 统计
     * @param permissionIds 权限ids
     * @return
     */
    Integer countByPermissionIds(@Param("ids") List permissionIds);


    /**
     * 批量新增
     * @param roleReq 权限角色信息
     * @return
     */
    Integer inserts(@Param("roleReq") RoleReq roleReq);


    /**
     * 批量删除关系
     * @param roleIds 角色ids
     */
    void deletesByRoleIds(@Param("roleIds") List roleIds);


    /**
     * 通过角色ids统计
     * @param roleIds 角色ids
     * @return
     */
    Integer countByRoleIds(@Param("roleIds") List roleIds);

    /**
     * 查询角色权限ids
     * @param roleId 角色id
     * @return
     */
    List<Integer> findPermissionIdsByRoleId(Integer roleId);


    /**
     * 根据权限删除关系
     * @param permissionIds 权限ids
     * @param id
     */
    void deletesByPermissionIds(@Param("permissionIds") List<Integer> permissionIds,@Param("roleId") Integer id);

    /**
     * 新增权限
     * @param missionIds 权限ids
     * @param id 角色id
     * @return
     */
    Integer insertList(@Param("missionIds") List<Integer> missionIds,@Param("roleId") Integer id);


}
