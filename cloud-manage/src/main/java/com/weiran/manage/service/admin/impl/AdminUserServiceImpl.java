package com.weiran.manage.service.admin.impl;

import com.weiran.manage.config.security.JwtUserService;
import com.weiran.manage.mapper.admin.*;
import com.weiran.manage.dto.admin.RoleDTO;
import com.weiran.manage.dto.admin.PermissionMenuDTO;
import com.weiran.manage.request.admin.*;
import com.weiran.manage.service.admin.AdminUserService;
import com.weiran.manage.utils.TreeHelper;
import com.weiran.manage.dto.admin.AdminUserDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final JwtUserService jwtUserService;

    private final AdminUserMapper adminUserMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRolePermissionMapper userRolePermissionMapper;

    private final RoleMapper roleMapper;

    private final PermissionMenuMapper permissionMenuMapper;


    @Override
    public Optional<AdminUserDTO> findByUsername(String username) {
        return adminUserMapper.findByUsername(username);
    }

    @Override
    public AdminUserDTO update(AdminUserInfoReq adminUserInfoReq, String username) {
        adminUserMapper.update(adminUserInfoReq,username);
        return adminUserMapper.findByUsername(username).orElse(null);
    }

    @Override
    public String updatePass(AdminUserPassReq adminUserDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(adminUserDTO.getPassword());
        adminUserMapper.updatePass(adminUserDTO.getUsername(),password);
        AdminUserDTO user = new AdminUserDTO();
        user.setUsername(adminUserDTO.getUsername());
        user.setPassword(adminUserDTO.getPassword());
        return jwtUserService.saveUserLoginInfo(user);
    }

    @Override
    public PageInfo<AdminUserDTO> findByAdminUsers(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page,pageSize);
        List<AdminUserDTO> adminUsers;
        if (StringUtils.isEmpty(search)) {
            adminUsers = adminUserMapper.findByAdminUsers();
        } else {
            adminUsers = adminUserMapper.findByAdminUsersLike(search);
        }
        return new PageInfo<>(adminUsers);
    }

    @Override
    public void switchIsBan(Integer id) {
        adminUserMapper.switchIsBan(id);
    }

    @Override
    public void createAdminUser(AdminUserReq adminUserReq) {
        // 创建管理员·加密密码
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(adminUserReq.getPassword());
        adminUserReq.setPassword(password);
        RoleDTO roleDTO = roleMapper.findById(adminUserReq.getRoleId());
        adminUserReq.setRole(roleDTO.getRole());
        adminUserMapper.insert(adminUserReq);
        List<Integer> rolePermissionIds = rolePermissionMapper.findPermissionIdsByRoleId(adminUserReq.getRoleId());
        // 创建权限关系
        userRolePermissionMapper.inserts(adminUserReq.getId(),adminUserReq.getRoleId(),rolePermissionIds);
    }

    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> userIds = Arrays.asList(split);
        userRolePermissionMapper.deletesByUserIds(userIds);
        adminUserMapper.deletesByIds(userIds);
    }

    @Override
    public void updateAdminUserInfo(AdminUserReq adminUserReq) {
        RoleDTO roleDTO = roleMapper.findById(adminUserReq.getRoleId());
        adminUserReq.setRole(roleDTO.getRole());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(adminUserReq.getPassword());
        adminUserReq.setPassword(password);
        adminUserMapper.updateAdminUserInfo(adminUserReq);
        // 删除已有权限
        AdminUserDTO userDTO = adminUserMapper.findById(adminUserReq.getId());
        if (!userDTO.getRole().equals(roleDTO.getRole())) {
            userRolePermissionMapper.deletesByUserIdAndRoleId(adminUserReq.getId(),userDTO.getRoles().get(0).getId());
            List<Integer> rolePermissionIds = rolePermissionMapper.findPermissionIdsByRoleId(adminUserReq.getRoleId());
            // 创建权限关系
            userRolePermissionMapper.inserts(adminUserReq.getId(),adminUserReq.getRoleId(),rolePermissionIds);
        }
    }

    @Override
    public void patchAdminUserPermission(AdminUserPermissionDTO adminUserPermissionDTO) {
        List<Integer> permissionIds = userRolePermissionMapper.findByUserId(adminUserPermissionDTO.getId());
        List<Integer> integers = Arrays.asList((Integer[]) ConvertUtils.convert(adminUserPermissionDTO.getPermissionIds(), Integer.class));
        // 相同权限不变
        List<Integer> saveList = integers.stream().filter(permissionIds::contains).collect(Collectors.toList());
        List<Integer> missionIds = new ArrayList<>(integers);
        missionIds.removeAll(saveList);
        // 多余权限新增
        if (missionIds.size() != 0) {
            userRolePermissionMapper.inserts(adminUserPermissionDTO.getId(),0,missionIds);
        }
        permissionIds.removeAll(saveList);
        if (permissionIds.size() != 0) {
            userRolePermissionMapper.deletesByPermissionIds(permissionIds,adminUserPermissionDTO.getId());
        }
    }

    @Override
    public List<PermissionMenuDTO> findByMenus(String username) {
        List<PermissionMenuDTO> roleMenus = permissionMenuMapper.findMenusByUsername(username);
        return TreeHelper.getSortedNodes(roleMenus);
    }
}
