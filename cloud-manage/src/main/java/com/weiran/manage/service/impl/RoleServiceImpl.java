package com.weiran.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.validation.BusinessValidation;
import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.mapper.RoleMapper;
import com.weiran.manage.mapper.RolePermissionMapper;
import com.weiran.manage.mapper.UserRolePermissionMapper;
import com.weiran.manage.request.RoleReq;
import com.weiran.manage.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
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

        if (roleReq.getId() == null || roleReq.getId() <= 0) {
            throw new IllegalArgumentException(ResponseEnum.PERMISSION_CREATE_ERROR.getMsg());
        }

        rolePermissionMapper.inserts(roleReq);
    }

    @Override
    public void deletes(String ids) {
        // 删除角色权限表，用户角色权限表，角色表
        String[] split = ids.split(COMMA);
        List<String> roleIds = Arrays.asList(split);
        BusinessValidation.isInvalid(userRolePermissionMapper.countByRoleIds(roleIds) > 0, ResponseEnum.PERMISSION_DELETES_ERROR);
        rolePermissionMapper.deletesByRoleIds(roleIds);
        userRolePermissionMapper.deletesByRoleIds(roleIds);
        roleMapper.deletesByIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleReq roleReq) {
        validateRoleRequest(roleReq);

        roleMapper.updateRole(roleReq);
        List<Integer> permissionIds = rolePermissionMapper.findPermissionIdsByRoleId(roleReq.getId());
        // 相同权限不变
        List<Integer> integers = Arrays.asList((Integer[]) ConvertUtils.convert(roleReq.getPermissionIds(), Integer.class));
        List<Integer> saveList = integers.stream().filter(permissionIds::contains).collect(Collectors.toList());
        List<Integer> missionIds = new ArrayList<>(integers);
        missionIds.removeAll(saveList);
        // 多余权限新增
        if (missionIds.size() != 0) {
            Integer rows = rolePermissionMapper.insertList(missionIds, roleReq.getId());
            BusinessValidation.isInvalid(rows <= 0, ResponseEnum.PERMISSION_UPDATE_ERROR);
        }
        // 少余权限删除
        permissionIds.removeAll(saveList);
        if (permissionIds.size() != 0) {
            rolePermissionMapper.deletesByPermissionIds(permissionIds, roleReq.getId());
        }
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
}
