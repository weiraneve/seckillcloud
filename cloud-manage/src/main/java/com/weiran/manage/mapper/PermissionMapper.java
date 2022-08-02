package com.weiran.manage.mapper;

import com.weiran.manage.request.PermissionReq;
import com.weiran.manage.dto.PermissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PermissionMapper {

    /**
     * 查询所有权限信息
     */
    List<PermissionDTO> findByPermissions();


    /**
     * 模糊查询权限
     */
    List<PermissionDTO> findPermissionsLikeBySearch(String search);


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
    void deletes(@Param("ids") List<String> permissionIds);


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
