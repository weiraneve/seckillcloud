package com.weiran.manage.mapper;

import com.weiran.manage.request.AdminUserInfoReq;
import com.weiran.manage.request.AdminUserReq;
import com.weiran.manage.dto.AdminUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminUserMapper {

    /**
     * 查询管理员
     */
    AdminUserDTO findById(Integer userId);

    /**
     * 查询用户
     */
    Optional<AdminUserDTO> findByUsername(String username);

    /**
     * 更新管理员信息
     */
    void update(@Param("adminUser") AdminUserInfoReq adminUserInfoReq, @Param("username") String username);


    /**
     * 修改密码
     */
    void updatePass(@Param("username") String username,@Param("password") String password);


    /**
     * 查询管理员
     */
    List<AdminUserDTO> findByAdminUsers();


    /**
     * 搜索管理员
     */
    List<AdminUserDTO> findByAdminUsersLike(@Param("search") String search);


    /**
     * 禁用/启用
     */
    void switchIsBan(Integer id);


    /**
     * 新增管理员
     */
    void insert(@Param("adminUser") AdminUserReq adminUserReq);


    /**
     * 批量删除管理员
     */
    void deletesByIds(@Param("userIds") List<String> userIds);


    /**
     * 修改管理员信息
     */
    void updateAdminUserInfo(@Param("adminUser") AdminUserReq adminUserReq);


}
