package com.weiran.manage.mapper;

import com.weiran.manage.request.MenuReq;
import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface PermissionMenuMapper {


    /**
     * 查询所有权限菜单
     */
    List<PermissionMenuDTO> findByRoleMenus();

    /**
     * 模糊搜索菜单
     */
    List<PermissionMenuDTO> findByRoleMenusLike(String search);


    /**
     * 统计菜单
     */
    Integer countByPermissionIds(@Param("ids") List permissionIds);

    /**
     * 查询所有树形结构菜单
     */
    List<TreeRoleMenuDTO> findRoleMenus();

    /**
     * 创建权限
     */
    Integer creatMenu(@Param("menu") MenuReq menuReq);

    /**
     * 删除菜单
     */
    void deleteById(String id);


    /**
     * 修改菜单
     */
    Integer updateMenu(@Param("menu") MenuReq menuReq);


    /**
     * 查询菜单
     */
    List<PermissionMenuDTO> findMenusByUsername(String username);


}
