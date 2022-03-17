package com.weiran.manage.mapper.admin;

import com.weiran.manage.dto.admin.RoleDTO;
import com.weiran.manage.request.admin.RoleReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface RoleMapper {

    /**
     * 查询角色
     * @param roleId 角色id
     * @return
     */
    RoleDTO findById(Integer roleId);

    /**
     * 查询角色
     * @return 角色
     */
    List<RoleDTO> findByRoles();


    /**
     * 模糊搜索角色
     * @param search 搜索关键字
     * @return 角色
     */
    List<RoleDTO> findByRolesLike(String search);


    /**
     * 查询角色
     * @param roles 角色
     * @return
     */
    List<RoleDTO> findRolesByRoles(String roles);

    /**
     * 新增角色信息
     * @param roleReq 角色信息
     * @return
     */
    Integer createRole(@Param("role") RoleReq roleReq);


    /**
     * 批量删除角色
     * @param roleIds 角色ids
     */
    void deletesByIds(@Param("roleIds")List roleIds);


    /**
     * 修改角色信息
     * @param roleReq 角色信息
     * @return
     */
    void updateRole(@Param("role") RoleReq roleReq);

}
