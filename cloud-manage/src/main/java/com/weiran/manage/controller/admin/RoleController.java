package com.weiran.manage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.weiran.common.obj.Result;
import com.weiran.manage.dto.RoleDTO;
import com.weiran.manage.request.RoleReq;
import com.weiran.manage.service.RoleService;
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
@Api("角色相关")
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','ROLE_ADMIN_USER')")
    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result<List<RoleDTO>> findAll() {
        List<RoleDTO> roles = roleService.findAll();
        return Result.success(roles);
    }

    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','ROLE_ADMIN_USER')")
    @ApiOperation("角色列表")
    @GetMapping
    public Result<PageInfo<RoleDTO>> findByRoles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                                 String search) {
        PageInfo<RoleDTO> roles = roleService.findByRoles(page, pageSize, search);
        return Result.success(roles);
    }

    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @ApiOperation("修改角色")
    @PutMapping
    public Result<Object> updateRole(@RequestBody RoleReq roleReq) {
        roleService.updateRole(roleReq);
        return Result.success();
    }


    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @ApiOperation("删除角色")
    @DeleteMapping
    public Result<Object> deletes(@RequestParam String ids) {
        roleService.deletes(ids);
        return Result.success();
    }

    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @ApiOperation("新增角色")
    @PostMapping
    public Result<Object> createRole(@RequestBody RoleReq roleReq) {
        roleService.createRole(roleReq);
        return Result.success();
    }

}
