package com.weiran.manage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.manage.dto.AdminUserDTO;
import com.weiran.manage.request.AdminUserPermissionReq;
import com.weiran.manage.request.AdminUserReq;
import com.weiran.manage.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api("管理员相关")
@RequiredArgsConstructor
@RequestMapping("/adminUser")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','ACCOUNT_ADMIN_USER')")
    @ApiOperation("管理员列表")
    @GetMapping
    public Result<PageInfo<AdminUserDTO>> findByAdminUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize, @RequestParam(required = false) String search) {
        PageInfo<AdminUserDTO> adminUsers = adminUserService.findByAdminUsers(page, pageSize, search);
        return Result.success(adminUsers);
    }

    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @PutMapping
    @ApiOperation("修改管理员")
    public Result<Object> updateAdminUserInfo(@RequestBody @Valid AdminUserReq adminUserReq) {
        adminUserService.updateAdminUserInfo(adminUserReq);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @ApiOperation("修改管理员权限")
    @PatchMapping
    public Result<Object> patchAdminUserPermission(@RequestBody @Valid AdminUserPermissionReq adminUserPermissionReq) {
        adminUserService.patchAdminUserPermission(adminUserPermissionReq);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @ApiOperation("批量删除管理员")
    @DeleteMapping
    public Result<Object> deletes(@RequestParam String ids) {
        adminUserService.deletes(ids);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @ApiOperation("新增管理员·关联角色")
    @PostMapping
    public Result<Object> createAdminUser(@RequestBody @Valid AdminUserReq adminUserReq) {
        adminUserService.createAdminUser(adminUserReq);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @ApiOperation("管理禁用/启用")
    @PatchMapping("/{id}")
    public Result<Object> switchIsBan(@PathVariable Integer id) {
        adminUserService.switchIsBan(id);
        return Result.success();
    }


}
