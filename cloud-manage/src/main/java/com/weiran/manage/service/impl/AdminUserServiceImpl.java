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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final int DEFAULT_ROLE_ID = 0;

    @Override
    public Optional<AdminUserDTO> findByUsername(String username) {
        return adminUserMapper.findByUsername(username);
    }

    @Override
    public AdminUserDTO update(AdminUserInfoReq adminUserInfoReq, String username) {
        adminUserMapper.update(adminUserInfoReq, username);
        return adminUserMapper.findByUsername(username).orElse(null);
    }

    @Override
    public String updatePass(AdminUserPasswordUpdateReq adminUserPasswordUpdateReq) {
        String username = adminUserPasswordUpdateReq.getUsername();
        AdminUserDTO adminUserDTO = adminUserMapper.findByUsername(username).orElse(null);
        assert adminUserDTO != null;
        String oldEncodedPassword = adminUserDTO.getPassword();
        boolean isMatch = passwordEncoder.matches(adminUserPasswordUpdateReq.getOldPassword(), oldEncodedPassword);
        if (!isMatch) {
            return null;
        }
        String encodedNewPassword = encodePass(adminUserPasswordUpdateReq.getNewPassword());
        adminUserMapper.updatePass(username, encodedNewPassword);
        return jwtUserService.saveUserLoginInfo(createDTOWithPassword(adminUserPasswordUpdateReq, encodedNewPassword));
    }

    @Override
    public PageInfo<AdminUserDTO> findByAdminUsers(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page, pageSize);
        List<AdminUserDTO> adminUsers = StringUtils.isEmpty(search) ?
                adminUserMapper.findByAdminUsers() : adminUserMapper.findByAdminUsersLike(search);
        return new PageInfo<>(adminUsers);
    }

    @Override
    public void switchIsBan(Integer id) {
        adminUserMapper.switchIsBan(id);
    }

    @Override
    public void createAdminUser(AdminUserReq adminUserReq) {
        adminUserReq.setPassword(encodePass(adminUserReq.getPassword()));
        setRoleForAdminUserRequest(adminUserReq);
        adminUserMapper.insert(adminUserReq);
        updateUserRolePermissions(adminUserReq);
    }

    @Override
    public void deletes(String ids) {
        List<String> userIds = Arrays.asList(ids.split(","));
        userRolePermissionMapper.deletesByUserIds(userIds);
        adminUserMapper.deletesByIds(userIds);
    }

    @Override
    public void updateAdminUserInfo(AdminUserReq adminUserReq) {
        setRoleForAdminUserRequest(adminUserReq);
        adminUserMapper.updateAdminUserInfo(adminUserReq);
        updateAdminUserPermissionBasedOnRole(adminUserReq);
    }

    @Override
    public void patchAdminUserPermission(AdminUserPermissionReq adminUserPermissionReq) {
        adjustUserPermissions(adminUserPermissionReq);
    }

    @Override
    public List<PermissionMenuDTO> findByMenus(String username) {
        return TreeHelper.getSortedNodes(permissionMenuMapper.findMenusByUsername(username));
    }

    private String encodePass(String password) {
        return passwordEncoder.encode(password);
    }

    private AdminUserDTO createDTOWithPassword(AdminUserPasswordUpdateReq adminUserPasswordUpdateReq, String encodedPassword) {
        AdminUserDTO user = new AdminUserDTO();
        user.setUsername(adminUserPasswordUpdateReq.getUsername());
        user.setPassword(encodedPassword);
        return user;
    }

    private void setRoleForAdminUserRequest(AdminUserReq adminUserReq) {
        RoleDTO roleDTO = roleMapper.findById(adminUserReq.getRoleId());
        adminUserReq.setRole(roleDTO.getRole());
    }

    private void updateUserRolePermissions(AdminUserReq adminUserReq) {
        List<Integer> rolePermissionIds = rolePermissionMapper.findPermissionIdsByRoleId(adminUserReq.getRoleId());
        userRolePermissionMapper.inserts(adminUserReq.getId(), adminUserReq.getRoleId(), rolePermissionIds);
    }

    private void updateAdminUserPermissionBasedOnRole(AdminUserReq adminUserReq) {
        AdminUserDTO currentUserDTO = adminUserMapper.findById(adminUserReq.getId());
        RoleDTO newRoleDTO = roleMapper.findById(adminUserReq.getRoleId());

        if (!currentUserDTO.getRole().equals(newRoleDTO.getRole())) {
            userRolePermissionMapper.deletesByUserIdAndRoleId(adminUserReq.getId(), currentUserDTO.getRoles().get(0).getId());
            updateUserRolePermissions(adminUserReq);
        }
    }

    private void adjustUserPermissions(AdminUserPermissionReq adminUserPermissionReq) {
        List<Integer> currentPermissionIds = userRolePermissionMapper.findByUserId(adminUserPermissionReq.getId());
        List<Integer> newPermissionIds = Arrays.stream(adminUserPermissionReq.getPermissionIds())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> unchangedPermissions = newPermissionIds.stream().filter(currentPermissionIds::contains).collect(Collectors.toList());
        List<Integer> addedPermissions = new ArrayList<>(newPermissionIds);
        addedPermissions.removeAll(unchangedPermissions);

        if (!addedPermissions.isEmpty()) {
            userRolePermissionMapper.inserts(adminUserPermissionReq.getId(), DEFAULT_ROLE_ID, addedPermissions);
        }

        currentPermissionIds.removeAll(unchangedPermissions);
        if (!currentPermissionIds.isEmpty()) {
            userRolePermissionMapper.deletesByPermissionIds(currentPermissionIds, adminUserPermissionReq.getId());
        }
    }
}
