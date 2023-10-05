package com.weiran.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.validation.CustomValidation;
import com.weiran.manage.dto.PermissionDTO;
import com.weiran.manage.mapper.PermissionMapper;
import com.weiran.manage.mapper.PermissionMenuMapper;
import com.weiran.manage.mapper.RolePermissionMapper;
import com.weiran.manage.mapper.UserRolePermissionMapper;
import com.weiran.manage.request.PermissionReq;
import com.weiran.manage.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;
    private final PermissionMenuMapper permissionMenuMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public PageInfo<PermissionDTO> findByPermissions(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page, pageSize);
        List<PermissionDTO> permissions = StringUtils.isEmpty(search) ?
                permissionMapper.findByPermissions() :
                permissionMapper.findPermissionsLikeBySearch(search);
        return new PageInfo<>(permissions);
    }

    @Override
    public Result<Object> createPermission(PermissionReq permissionReq) {
        permissionMapper.findByPermission(permissionReq.getPermission())
                .ifPresent(permission -> Result.fail(ResponseEnum.PERMISSION_EXIST_ERROR));
        permissionMapper.insert(permissionReq);
        return Result.success();
    }

    @Override
    public void deletes(String ids) {
        List<String> permissionIds = Arrays.asList(ids.split(","));
        validateDeletion(permissionIds);
        permissionMapper.deletes(permissionIds);
    }

    private void validateDeletion(List<String> permissionIds) {
        boolean isMenuAssociated = permissionMenuMapper.countByPermissionIds(permissionIds) > 0;
        boolean isRoleAssociated = rolePermissionMapper.countByPermissionIds(permissionIds) > 0;
        boolean isUserRoleAssociated = userRolePermissionMapper.countByPermissionIds(permissionIds) > 0;

        CustomValidation.isInvalid(isMenuAssociated || isRoleAssociated || isUserRoleAssociated, ResponseEnum.PERMISSION_DELETES_ERROR);
    }

    @Override
    public Result<Object> update(PermissionReq permissionReq) {
        permissionMapper.findByPermissionAndId(permissionReq)
                .ifPresent(permission -> Result.fail(ResponseEnum.PERMISSION_EXIST_ERROR));
        permissionMapper.update(permissionReq);
        return Result.success();
    }

    @Override
    public List<PermissionDTO> findAll() {
        return permissionMapper.findAll();
    }
}
