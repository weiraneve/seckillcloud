package com.weiran.manage.controller.admin;

import com.weiran.common.obj.Result;
import com.weiran.manage.dto.PermissionMenuDTO;
import com.weiran.manage.dto.TreeRoleMenuDTO;
import com.weiran.manage.request.MenuReq;
import com.weiran.manage.service.PermissionMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("权限与菜单相关")
@RequiredArgsConstructor
@RequestMapping("/roleMenu")
public class PermissionMenuController {

    private final PermissionMenuService permissionMenuService;

    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','MENU_ADMIN_USER')")
    @ApiOperation("菜单选择")
    @GetMapping("/getMenus")
    public Result<List<TreeRoleMenuDTO>> getMenus() {
        List<TreeRoleMenuDTO> treeRoleMenus = permissionMenuService.findRoleMenus();
        return Result.success(treeRoleMenus);
    }

    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','MENU_ADMIN_USER')")
    @ApiOperation("菜单列表(目录/菜单)")
    @GetMapping
    public Result<List<PermissionMenuDTO>> findByRoleMenus(String search) {
        List<PermissionMenuDTO> roleMenus = permissionMenuService.findByRoleMenus(search);
        return Result.success(roleMenus);
    }

    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @ApiOperation("修改菜单")
    @PutMapping
    public Result<Object> updateMenu(@RequestBody MenuReq menuReq) {
        permissionMenuService.updateMenu(menuReq);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @ApiOperation("删除菜单")
    @DeleteMapping
    public Result<Object> delete(@RequestParam String id) {
        permissionMenuService.deleteById(id);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @ApiOperation("新增菜单")
    @PostMapping
    public Result<Object> creatMenu(@RequestBody MenuReq menuReq) {
        permissionMenuService.createMenu(menuReq);
        return Result.success();
    }
}
