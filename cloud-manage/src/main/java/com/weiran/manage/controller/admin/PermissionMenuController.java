package com.weiran.manage.controller.admin;

import com.weiran.common.obj.Result;
import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;
import com.weiran.manage.request.MenuReq;
import com.weiran.manage.service.PermissionMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限与菜单相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roleMenu")
public class PermissionMenuController {

    private final PermissionMenuService permissionMenuService;

    /**
     * 菜单选择
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','MENU_ADMIN_USER')")
    @GetMapping("/getMenus")
    public Result<List<TreeRoleMenuDTO>> getMenus() {
        List<TreeRoleMenuDTO> treeRoleMenus = permissionMenuService.findRoleMenus();
        return Result.success(treeRoleMenus);
    }

    /**
     * 菜单列表(目录/菜单)
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','MENU_ADMIN_USER')")
    @GetMapping
    public Result<List<PermissionMenuDTO>> findByRoleMenus(String search) {
        List<PermissionMenuDTO> roleMenus = permissionMenuService.findByRoleMenus(search);
        return Result.success(roleMenus);
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @PutMapping
    public Result<Object> updateMenu(@RequestBody MenuReq menuReq) {
        permissionMenuService.updateMenu(menuReq);
        return  Result.success();
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public Result<Object> delete(@RequestParam String id) {
        permissionMenuService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @PostMapping
    public Result<Object> creatMenu(@RequestBody MenuReq menuReq) {
        permissionMenuService.creatMenu(menuReq);
        return Result.success();
    }
}
