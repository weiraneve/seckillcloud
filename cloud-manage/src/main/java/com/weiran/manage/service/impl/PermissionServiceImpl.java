package com.weiran.manage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.validation.BusinessValidation;
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
import java.util.Optional;


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
        List<PermissionDTO> permissions;
        if (StringUtils.isEmpty(search)) {
            permissions = permissionMapper.findByPermissions();
        } else {
            permissions = permissionMapper.findPermissionsLikeBySearch(search);
        }
        return new PageInfo<>(permissions);
    }

    @Override
    public boolean createPermission(PermissionReq permissionReq) {
        Optional<PermissionDTO> permission = permissionMapper.findByPermission(permissionReq.getPermission());
        BusinessValidation.isInvalid(ObjectUtil.isNull(permission.isPresent()), ResponseEnum.PERMISSION_EXIST_ERROR);
        Integer row = permissionMapper.insert(permissionReq);
        return row > 0;
    }

    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> permissionIds = Arrays.asList(split);
        // 删除关联菜单
        Integer roleMenu = permissionMenuMapper.countByPermissionIds(permissionIds);
        BusinessValidation.isInvalid(roleMenu > 0, ResponseEnum.PERMISSION_DELETES_ERROR);
        // 删除角色权限
        Integer role = rolePermissionMapper.countByPermissionIds(permissionIds);
        BusinessValidation.isInvalid(role > 0, ResponseEnum.PERMISSION_DELETES_ERROR);
        // 删除用户权限
        Integer userRole = userRolePermissionMapper.countByPermissionIds(permissionIds);
        BusinessValidation.isInvalid(userRole > 0, ResponseEnum.PERMISSION_DELETES_ERROR);
        // 删除权限
        permissionMapper.deletes(permissionIds);
    }

    @Override
    public boolean update(PermissionReq permissionReq) {
        Optional<PermissionDTO> permission = permissionMapper.findByPermissionAndId(permissionReq);
        BusinessValidation.isInvalid(permission.isPresent(), ResponseEnum.PERMISSION_EXIST_ERROR);
        Integer row = permissionMapper.update(permissionReq);
        return row > 0;
    }

    @Override
    public List<PermissionDTO> findAll() {
        return permissionMapper.findAll();
    }
}
