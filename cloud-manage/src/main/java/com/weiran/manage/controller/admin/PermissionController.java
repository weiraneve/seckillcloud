package com.weiran.manage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.admin.PermissionDTO;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.request.admin.PermissionReq;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.admin.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 权限列表
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','PERMISSION_ADMIN_USER')")
    @GetMapping
    public ResultVO findByPermissions(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
                                      String search) {
        PageInfo<PermissionDTO> permissions = permissionService.findByPermissions(page, pageSize,search);
        return ResultVO.success(permissions);
    }

    /**
     * 修改权限
     */
    @PreAuthorize("hasAnyAuthority('SETTING_UPDATE','ROLE_SUPER_ADMIN')")
    @PutMapping
    public ResultVO update(@RequestBody PermissionReq permissionReq) {
        boolean updateSuccess = permissionService.update(permissionReq);
        return updateSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.PERMISSION_UPDATE_ERROR);
    }

    /**
     * 删除权限
     */
    @PreAuthorize("hasAnyAuthority('SRTTING_DELETE','ROLE_SUPER_ADMIN')")
    @DeleteMapping
    public ResultVO deletes(@RequestParam String ids) {
        permissionService.deletes(ids);
        return ResultVO.success();
    }

    /**
     * 新增权限
     */
    @PreAuthorize("hasAnyAuthority('SETTING_ADD','ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResultVO createPermission(@RequestBody PermissionReq permissionReq) {
        boolean createSuccess = permissionService.createPermission(permissionReq);
        return createSuccess ? ResultVO.success() : ResultVO.fail(ResponseEnum.PERMISSION_CREATE_ERROR);
    }

    /**
     * 查询所有权限
     */
    @PreAuthorize("hasAnyAuthority('SETTING_SELECT','ROLE_SUPER_ADMIN','PERMISSION_ADMIN_USER')")
    @GetMapping("/findAll")
    public ResultVO findAll() {
        List<PermissionDTO> permissionDTOS = permissionService.findAll();
        return ResultVO.success(permissionDTOS);
    }
}




