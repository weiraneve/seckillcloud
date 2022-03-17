package com.weiran.manage.mapper.admin;

import com.weiran.manage.request.admin.PermissionReq;
import com.weiran.manage.dto.admin.PermissionDTO;
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
     * @param permissionId 权限id
     * @return 权限
     */
    PermissionDTO findById(Integer permissionId);

    /**
     * 通过角色id查询权限
     * @param roleId
     * @return
     */
    List<PermissionDTO> findByIdRoleId(Integer roleId);

    /**
     * 查询所有权限信息
     * @return 权限
     */
    List<PermissionDTO> findByPermissions();


    /**
     * 模糊查询权限
     * @param search 搜索关键字
     * @return 权限
     */
    List<PermissionDTO> findPermissionsLikeBySearch(String search);

    /**
     * 查询管理员权限
     * @param adminUserId 管理员id
     * @return
     */
    List<PermissionDTO> findByAdminUserId(Integer adminUserId);

    /**
     * 查询权限
     * @param permission 权限
     * @return
     */
    Optional<PermissionDTO> findByPermission(String permission);


    /**
     * 新增权限
     * @param permissionReq 权限
     * @return
     */
    Integer insert(@Param("permission") PermissionReq permissionReq);


    /**
     * 批量删除权限
     * @param permissionIds 权限ids
     */
    void deletes(@Param("ids") List permissionIds);


    /**
     * 查询权限
     * @param permissionReq 权限信息
     * @return
     */
    Optional<PermissionDTO> findByPermissionAndId(@Param("permission") PermissionReq permissionReq);

    /**
     * 修改权限信息
     * @param permissionReq 权限信息
     * @return
     */
    Integer update(@Param("permission") PermissionReq permissionReq);

    /**
     * 查询所有
     * @return
     */
    List<PermissionDTO> findAll();


}
