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


@Service
@RequiredArgsConstructor
public class PermissionMenuServiceImpl implements PermissionMenuService {

    private final PermissionMenuMapper permissionMenuMapper;

    @Override
    public List<PermissionMenuDTO> findByRoleMenus(String search) {
        List<PermissionMenuDTO> roleMenuS;
        if (StringUtils.isEmpty(search)) {
            roleMenuS = permissionMenuMapper.findByRoleMenus();
        } else {
            roleMenuS = permissionMenuMapper.findByRoleMenusLike(search);
        }
        return TreeHelper.getSortedNodes(roleMenuS);
    }

    @Override
    public List<TreeRoleMenuDTO> findRoleMenus() {
        List<TreeRoleMenuDTO> treeRoleMenuDTOS = permissionMenuMapper.findRoleMenus();
        return TreeHelper.getSortedTreeNodes(treeRoleMenuDTOS);
    }

    @Override
    public void creatMenu(MenuReq menuReq) {
        menuReq.setLevel(menuReq.getLevel() + 1);
        permissionMenuMapper.creatMenu(menuReq);
    }

    @Override
    public void deleteById(String id) {
        // 并且删除子菜单
        permissionMenuMapper.deleteById(id);
    }

    @Override
    public void updateMenu(MenuReq menuReq) {
        permissionMenuMapper.updateMenu(menuReq);
    }
}
