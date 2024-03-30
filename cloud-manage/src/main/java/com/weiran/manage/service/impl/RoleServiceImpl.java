package com.weiran.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.validation.CustomValidation;
import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.mapper.RoleMapper;
import com.weiran.manage.mapper.RolePermissionMapper;
import com.weiran.manage.mapper.UserRolePermissionMapper;
import com.weiran.manage.request.RoleReq;
import com.weiran.manage.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String COMMA = ",";

    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public PageInfo<RoleDTO> findByRoles(Integer page, Integer pageSize, String search) {
        validatePageParameters(page, pageSize);
        PageHelper.startPage(page, pageSize);
        List<RoleDTO> roles = (search == null || search.isEmpty()) ? roleMapper.findByRoles() : roleMapper.findByRolesLike(search);
        return new PageInfo<>(roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(RoleReq roleReq) {
        validateRoleRequest(roleReq);
        roleMapper.createRole(roleReq);
        ensureRoleCreationSuccess(roleReq);
        rolePermissionMapper.inserts(roleReq);
    }

    @Override
    public void deletes(String ids) {
        List<String> roleIds = extractIds(ids);
        ensureNoAssociatedPermissions(roleIds);
        deleteAssociatedRoleEntries(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleReq roleReq) {
        validateRoleRequest(roleReq);
        roleMapper.updateRole(roleReq);
        handlePermissionsUpdate(roleReq);
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleMapper.findByRoles();
    }

    private void validatePageParameters(Integer page, Integer pageSize) {
        if (page == null || pageSize == null || page <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException(ResponseEnum.INVALID_PAGE_OR_PAGE_SIZE.getMsg());
        }
    }

    private void validateRoleRequest(RoleReq roleReq) {
        if (roleReq == null) {
            throw new IllegalArgumentException(ResponseEnum.ROLE_NOT_FOUND.getMsg());
        }
    }

    private void ensureRoleCreationSuccess(RoleReq roleReq) {
        if (roleReq.getId() == null || roleReq.getId() <= 0) {
            throw new IllegalArgumentException(ResponseEnum.PERMISSION_CREATE_ERROR.getMsg());
        }
    }

    private List<String> extractIds(String ids) {
        return Arrays.asList(ids.split(COMMA));
    }

    private void ensureNoAssociatedPermissions(List<String> roleIds) {
        boolean hasAssociatedPermissions = userRolePermissionMapper.countByRoleIds(roleIds) > 0;
        CustomValidation.isInvalid(hasAssociatedPermissions, ResponseEnum.PERMISSION_DELETES_ERROR);
    }

    private void deleteAssociatedRoleEntries(List<String> roleIds) {
        rolePermissionMapper.deletesByRoleIds(roleIds);
        userRolePermissionMapper.deletesByRoleIds(roleIds);
        roleMapper.deletesByIds(roleIds);
    }

    private void handlePermissionsUpdate(RoleReq roleReq) {
        List<Integer> existingPermissionIds = rolePermissionMapper.findPermissionIdsByRoleId(roleReq.getId());
        List<Integer> requestedPermissionIds = Arrays.stream(roleReq.getPermissionIds())
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        updateNewPermissions(roleReq, existingPermissionIds, requestedPermissionIds);
        removeUnwantedPermissions(roleReq, existingPermissionIds, requestedPermissionIds);
    }

    private void updateNewPermissions(RoleReq roleReq, List<Integer> existingPermissionIds, List<Integer> requestedPermissionIds) {
        List<Integer> newPermissions = requestedPermissionIds.stream()
                .filter(permissionId -> !existingPermissionIds.contains(permissionId))
                .collect(Collectors.toList());
        int userId = userRolePermissionMapper.findByRoleId(roleReq.getId());
        if (!newPermissions.isEmpty()) {
            int rows = rolePermissionMapper.insertList(newPermissions, roleReq.getId());
            userRolePermissionMapper.inserts(userId, roleReq.getId(), newPermissions);
            CustomValidation.isInvalid(rows <= 0, ResponseEnum.PERMISSION_UPDATE_ERROR);
        } else {
            List<Integer> differencePermissions = new ArrayList<>(existingPermissionIds);
            differencePermissions.removeAll(requestedPermissionIds);
            userRolePermissionMapper.deletesByUserIdAndRoleIdAndPermissionIds(userId, roleReq.getId(), differencePermissions);
        }
    }

    private void removeUnwantedPermissions(RoleReq roleReq, List<Integer> existingPermissionIds, List<Integer> requestedPermissionIds) {
        List<Integer> permissionsToRemove = existingPermissionIds.stream()
                .filter(permissionId -> !requestedPermissionIds.contains(permissionId))
                .collect(Collectors.toList());
        if (!permissionsToRemove.isEmpty()) {
            rolePermissionMapper.deletesByPermissionIds(permissionsToRemove, roleReq.getId());
        }
    }
}
