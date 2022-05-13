package com.weiran.manage.service.admin.impl;

import com.weiran.common.exception.CustomizeException;
import com.weiran.manage.mapper.admin.RolePermissionMapper;
import com.weiran.manage.mapper.admin.UserRolePermissionMapper;
import com.weiran.manage.dto.admin.RoleDTO;
import com.weiran.manage.request.admin.RoleReq;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.manage.service.admin.RoleService;
import com.weiran.manage.mapper.admin.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public PageInfo<RoleDTO> findByRoles(Integer page, Integer pageSize, String search) {
        PageHelper.startPage(page,pageSize);
        List<RoleDTO> roles;
        if (StringUtils.isEmpty(search)) {
            roles = roleMapper.findByRoles();
        } else {
            roles = roleMapper.findByRolesLike(search);
        }
        return new PageInfo<>(roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createRole(RoleReq roleReq) {
        roleMapper.createRole(roleReq);
        if (roleReq.getId() <= 0) {
            throw new CustomizeException(ResponseEnum.PERMISSION_CREATE_ERROR);
        }
        Integer rows = rolePermissionMapper.inserts(roleReq);
        return rows > 0;
    }

    @Override
    public void deletes(String ids) {
        // 删除角色权限表，用户角色权限表，角色表
        String[] split = ids.split(",");
        List<String> roleIds = Arrays.asList(split);
        if (userRolePermissionMapper.countByRoleIds(roleIds) > 0) {
            throw new CustomizeException(ResponseEnum.PERMISSION_DELETES_ERROR);
        }
        rolePermissionMapper.deletesByRoleIds(roleIds);
        userRolePermissionMapper.deletesByRoleIds(roleIds);
        roleMapper.deletesByIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleReq roleReq) {
        roleMapper.updateRole(roleReq);
        List<Integer> permissionIds = rolePermissionMapper.findPermissionIdsByRoleId(roleReq.getId());
        // 相同权限不变
        List<Integer> integers = Arrays.asList((Integer[]) ConvertUtils.convert(roleReq.getPermissionIds(), Integer.class));
        List<Integer> saveList = integers.stream().filter(permissionIds::contains).collect(Collectors.toList());
        List<Integer> missionIds = new ArrayList<>(integers);
        missionIds.removeAll(saveList);
        // 多余权限新增
        if (missionIds.size() != 0) {
            Integer rows = rolePermissionMapper.insertList(missionIds,roleReq.getId());
            if (rows <= 0) {
                throw new CustomizeException(ResponseEnum.PERMISSION_UPDATE_ERROR);
            }
        }
        // 少余权限删除
        permissionIds.removeAll(saveList);
        if (permissionIds.size() != 0) {
            rolePermissionMapper.deletesByPermissionIds(permissionIds,roleReq.getId());
        }
        return true;
    }

    @Override
    public List<RoleDTO> findAll() {
        return roleMapper.findByRoles();
    }
}
