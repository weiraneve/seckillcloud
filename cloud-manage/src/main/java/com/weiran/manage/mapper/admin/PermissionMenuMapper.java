package com.weiran.manage.mapper.admin;

import com.weiran.manage.request.admin.MenuReq;
import com.weiran.manage.dto.admin.PermissionMenuDTO;
import com.weiran.manage.dto.admin.TreeRoleMenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface PermissionMenuMapper {


    /**
     * 查询所有权限菜单
     * @return 权限菜单
     */
    List<PermissionMenuDTO> findByRoleMenus();

    /**
     * 模糊搜索菜单
     * @param search 搜索关键字
     * @return 菜单
     */
    List<PermissionMenuDTO> findByRoleMenusLike(String search);


    /**
     * 统计菜单
     * @param permissionIds 权限ids
     * @return
     */
    Integer countByPermissionIds(@Param("ids") List permissionIds);

    /**
     * 查询所有树形结构菜单
     * @return
     */
    List<TreeRoleMenuDTO> findRoleMenus();

    /**
     * 创建权限
     * @param menuReq 权限信息
     * @return row
     */
    Integer creatMenu(@Param("menu") MenuReq menuReq);

    /**
     * 删除菜单
     * @param id 菜单id
     */
    void deleteById(String id);


    /**
     * 修改菜单
     * @param menuReq 修改菜单
     * @return
     */
    Integer updateMenu(@Param("menu") MenuReq menuReq);


    /**
     * 查询菜单
     * @param username 账号
     * @return
     */
    List<PermissionMenuDTO> findMenusByUsername(String username);


}
