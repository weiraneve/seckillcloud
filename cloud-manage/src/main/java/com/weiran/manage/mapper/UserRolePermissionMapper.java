package com.weiran.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserRolePermissionMapper {


    /**
     * 统计
     */
    Integer countByPermissionIds(@Param("ids") List permissionIds);


    /**
     * 批量删除关系
     */
    void deletesByRoleIds(@Param("roleIds")List roleIds);


    /**
     * 通过角色ids统计
     */
    Integer countByRoleIds(@Param("roleIds") List roleIds);


    /**
     * 新增用户角色权限表
     */
    void inserts(@Param("userId") Integer userId,
                 @Param("roleId") Integer roleId,
                 @Param("permissionIds") List<Integer> rolePermissionIds);


    /**
     * 批量删除
     */
    void deletesByUserIds(@Param("userIds") List userIds);


    /**
     * 删除用户角色权限关系
     */
    void deletesByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);


    /**
     * 查询用户角色权限关系
     */
    List<Integer> findByUserId(Integer userId);


    /**
     * 批量删除关系
     */
    void deletesByPermissionIds(@Param("permissionIds") List<Integer> permissionIds,@Param("userId") Integer userId);


}
