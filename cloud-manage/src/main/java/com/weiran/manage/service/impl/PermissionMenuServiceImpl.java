package com.weiran.manage.service.impl;

import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;
import com.weiran.manage.mapper.PermissionMenuMapper;
import com.weiran.manage.request.MenuReq;
import com.weiran.manage.service.PermissionMenuService;
import com.weiran.manage.utils.TreeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PermissionMenuServiceImpl implements PermissionMenuService {

    private final PermissionMenuMapper permissionMenuMapper;

    @Override
    public List<PermissionMenuDTO> findByRoleMenus(String search) {
        List<PermissionMenuDTO> roleMenus = StringUtils.isEmpty(search) ?
                permissionMenuMapper.findByRoleMenus() :
                permissionMenuMapper.findByRoleMenusLike(search);
        return TreeHelper.getSortedNodes(roleMenus);
    }


    @Override
    public List<TreeRoleMenuDTO> findRoleMenus() {
        return TreeHelper.getSortedTreeNodes(permissionMenuMapper.findRoleMenus());
    }

    @Override
    public void createMenu(MenuReq menuReq) {
        if (menuReq != null) {
            menuReq.setLevel(Optional.ofNullable(menuReq.getLevel()).orElse(0) + 1);
            permissionMenuMapper.createMenu(menuReq);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteById(String id) {
        permissionMenuMapper.deleteById(id);
    }

    @Override
    public void updateMenu(MenuReq menuReq) {
        permissionMenuMapper.updateMenu(menuReq);
    }
}
