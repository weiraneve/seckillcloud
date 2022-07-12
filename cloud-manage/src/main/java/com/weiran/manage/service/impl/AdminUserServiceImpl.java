package com.weiran.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.manage.config.security.JwtUserService;
import com.weiran.manage.dto.AdminUserDTO;
import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.mapper.*;
import com.weiran.manage.request.*;
import com.weiran.manage.service.AdminUserService;
import com.weiran.manage.utils.TreeHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public String updatePass(AdminUserPassReq adminUserReq) {
        String password = encodePass(adminUserReq.getPassword());
        adminUserMapper.updatePass(adminUserReq.getUsername(), password);
        AdminUserDTO user = new AdminUserDTO();
        user.setUsername(adminUserReq.getUsername());
        user.setPassword(adminUserReq.getPassword());
        return jwtUserService.saveUserLoginInfo(user);
    }

    private String encodePass(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public PageInfo<AdminUserDTO> findByAdminUsers(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page, pageSize);
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
        String password = encodePass(adminUserReq.getPassword());
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
        String password = encodePass(adminUserReq.getPassword());
        adminUserReq.setPassword(password);
        adminUserMapper.updateAdminUserInfo(adminUserReq);
        // 删除已有权限
        AdminUserDTO userDTO = adminUserMapper.findById(adminUserReq.getId());
        if (!userDTO.getRole().equals(roleDTO.getRole())) {
            userRolePermissionMapper.deletesByUserIdAndRoleId(adminUserReq.getId(), userDTO.getRoles().get(0).getId());
            List<Integer> rolePermissionIds = rolePermissionMapper.findPermissionIdsByRoleId(adminUserReq.getRoleId());
            // 创建权限关系
            userRolePermissionMapper.inserts(adminUserReq.getId(),adminUserReq.getRoleId(), rolePermissionIds);
        }
    }

    @Override
    public void patchAdminUserPermission(AdminUserPermissionReq adminUserPermissionReq) {
        List<Integer> permissionIds = userRolePermissionMapper.findByUserId(adminUserPermissionReq.getId());
        List<Integer> integers = Arrays.asList((Integer[]) ConvertUtils.convert(adminUserPermissionReq.getPermissionIds(), Integer.class));
        // 相同权限不变
        List<Integer> saveList = integers.stream().filter(permissionIds::contains).collect(Collectors.toList());
        List<Integer> missionIds = new ArrayList<>(integers);
        missionIds.removeAll(saveList);
        // 多余权限新增
        if (missionIds.size() != 0) {
            userRolePermissionMapper.inserts(adminUserPermissionReq.getId(),0, missionIds);
        }
        permissionIds.removeAll(saveList);
        if (permissionIds.size() != 0) {
            userRolePermissionMapper.deletesByPermissionIds(permissionIds, adminUserPermissionReq.getId());
        }
    }

    @Override
    public List<PermissionMenuDTO> findByMenus(String username) {
        List<PermissionMenuDTO> roleMenus = permissionMenuMapper.findMenusByUsername(username);
        return TreeHelper.getSortedNodes(roleMenus);
    }
}
