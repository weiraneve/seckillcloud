package com.weiran.manage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.admin.RoleDTO;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.manage.request.admin.RoleReq;
import com.weiran.common.obj.Result;
import com.weiran.manage.service.admin.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 查询所有角色
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','ROLE_ADMIN_USER')")
    @GetMapping("/findAll")
    public Result<List<RoleDTO>> findAll() {
        List<RoleDTO> roles = roleService.findAll();
        return Result.success(roles);
    }

    /**
     * 角色列表
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','ROLE_ADMIN_USER')")
    @GetMapping
    public Result<PageInfo<RoleDTO>> findByRoles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                String search) {
        PageInfo<RoleDTO> roles = roleService.findByRoles(page, pageSize, search);
        return Result.success(roles);
    }

    /**
     * 修改角色
     */
    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @PutMapping
    public Result<Object> updateRole(@RequestBody RoleReq roleReq) {
        boolean updateSuccess = roleService.updateRole(roleReq);
        return updateSuccess ? Result.success() : Result.error(ResponseEnum.PERMISSION_UPDATE_ERROR);
    }


    /**
     * 删除角色
     */
    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public Result<Object> deletes(@RequestParam String ids) {
        roleService.deletes(ids);
        return Result.success();
    }

    /**
     * 新增角色
     */
    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @PostMapping
    public Result<Object> createRole(@RequestBody RoleReq roleReq) {
        boolean createSuccess = roleService.createRole(roleReq);
        return createSuccess ? Result.success() : Result.error(ResponseEnum.PERMISSION_CREATE_ERROR);
    }

}
