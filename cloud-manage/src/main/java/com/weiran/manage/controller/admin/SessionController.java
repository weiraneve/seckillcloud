package com.weiran.manage.controller.admin;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.manage.dto.AdminUserDTO;
import com.weiran.manage.request.AdminUserInfoReq;
import com.weiran.manage.request.AdminUserPasswordUpdateReq;
import com.weiran.manage.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * 后台管理员会话
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final AdminUserService adminUserService;

    /**
     * 获取用户信息
     */
    @GetMapping
    public Result<AdminUserDTO> index(Principal principal) {
        Optional<AdminUserDTO> adminUserDTO = adminUserService.findByUsername(principal.getName());
        return adminUserDTO.map(Result::success).orElseGet(() -> Result.fail(ResponseEnum.USER_NOT_FOUND));
    }

    /**
     * 查询用户是否存在
     */
    @GetMapping("/findByUsername")
    public Result<Object> findByUsername(@RequestParam String username) {
        Optional<AdminUserDTO> userDTO = adminUserService.findByUsername(username);
        if (!userDTO.isPresent()) {
            return Result.fail(ResponseEnum.USER_NOT_FOUND);
        }
        return Result.success();
    }

    /**
     * 更新用户信息
     */
    @PatchMapping("/update")
    public Result<AdminUserDTO> update(@RequestBody AdminUserInfoReq adminUserInfoReq, Principal principal) {
        AdminUserDTO adminUserDTO = adminUserService.update(adminUserInfoReq, principal.getName());
        return Result.success(adminUserDTO);
    }

    /**
     * 修改密码
     */
    @PatchMapping("/updatePass")
    public Result<String> updatePass(@RequestBody AdminUserPasswordUpdateReq adminUserPasswordUpdateReq) {
        return Optional.ofNullable(adminUserService.updatePass(adminUserPasswordUpdateReq))
                .map(Result::success)
                .orElseGet(() -> Result.fail(ResponseEnum.USER_PASSWORD_VALID));
    }
}
