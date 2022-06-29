package com.weiran.manage.mapper;

import com.weiran.manage.request.PermissionReq;
import com.weiran.manage.dto.PermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Mapper
public interface PermissionMapper {

    /**
     * 查询权限信息
     */
    PermissionDTO findById(Integer permissionId);

    /**
     * 通过角色id查询权限
     */
    List<PermissionDTO> findByIdRoleId(Integer roleId);

    /**
     * 查询所有权限信息
     */
    List<PermissionDTO> findByPermissions();


    /**
     * 模糊查询权限
     */
    List<PermissionDTO> findPermissionsLikeBySearch(String search);

    /**
     * 查询管理员权限
     */
    List<PermissionDTO> findByAdminUserId(Integer adminUserId);

    /**
     * 查询权限
     */
    Optional<PermissionDTO> findByPermission(String permission);


    /**
     * 新增权限
     */
    Integer insert(@Param("permission") PermissionReq permissionReq);


    /**
     * 批量删除权限
     */
    void deletes(@Param("ids") List permissionIds);


    /**
     * 查询权限
     */
    Optional<PermissionDTO> findByPermissionAndId(@Param("permission") PermissionReq permissionReq);

    /**
     * 修改权限信息
     */
    Integer update(@Param("permission") PermissionReq permissionReq);

    /**
     * 查询所有
     */
    List<PermissionDTO> findAll();


}
