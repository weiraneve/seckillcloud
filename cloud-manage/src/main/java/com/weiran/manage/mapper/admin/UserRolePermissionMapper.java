package com.weiran.manage.mapper.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserRolePermissionMapper {


    /**
     * 统计
     * @param permissionIds 权限ids
     * @return
     */
    Integer countByPermissionIds(@Param("ids") List permissionIds);


    /**
     * 批量删除关系
     * @param roleIds 角色ids
     */
    void deletesByRoleIds(@Param("roleIds")List roleIds);


    /**
     * 通过角色ids统计
     * @param roleIds 角色ids
     * @return
     */
    Integer countByRoleIds(@Param("roleIds") List roleIds);


    /**
     * 新增用户角色权限表
     * @param userId 管理员id
     * @param roleId 角色id
     * @param rolePermissionIds 权限ids
     */
    void inserts(@Param("userId") Integer userId,
                 @Param("roleId") Integer roleId,
                 @Param("permissionIds") List<Integer> rolePermissionIds);


    /**
     * 批量删除
     * @param userIds 管理员第三
     */
    void deletesByUserIds(@Param("userIds") List userIds);


    /**
     * 删除用户角色权限关系
     * @param userId
     * @param roleId
     */
    void deletesByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


    /**
     * 查询用户角色权限关系
     * @param userId
     * @return
     */
    List<Integer> findByUserId(Integer userId);


    /**
     * 批量删除关系
     * @param permissionIds
     * @param userId
     */
    void deletesByPermissionIds(@Param("permissionIds") List<Integer> permissionIds,@Param("userId") Integer userId);


}
