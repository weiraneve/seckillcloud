package com.weiran.manage.controller.admin;

import com.weiran.manage.dto.admin.AdminUserDTO;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.exception.CustomizeException;
import com.weiran.manage.request.admin.AdminUserInfoReq;
import com.weiran.manage.request.admin.AdminUserPassReq;
import com.weiran.manage.response.ResultVO;
import com.weiran.manage.service.admin.AdminUserService;
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
    public ResultVO index(Principal principal) {
        AdminUserDTO adminUserDTO = adminUserService.findByUsername(principal.getName()).get();
        return ResultVO.success(adminUserDTO);
    }

    /**
     * 查询用户是否存在
     */
    @GetMapping("/findByUsername")
    public ResultVO findByUsername(@RequestParam String username) {
        Optional<AdminUserDTO> userDTO =  adminUserService.findByUsername(username);
        if (!userDTO.isPresent()) {
            throw new CustomizeException(ResponseEnum.USER_NOT_FOUND);
        }
        return ResultVO.success();
    }

    /**
     * 更新用户信息
     */
    @PatchMapping("/update")
    public ResultVO update(@RequestBody AdminUserInfoReq adminUserInfoReq, Principal principal) {
        AdminUserDTO adminUserDTO = adminUserService.update(adminUserInfoReq,principal.getName());
        return ResultVO.success(adminUserDTO);
    }

    /**
     * 修改密码
     */
    @PatchMapping("/updatePass")
    public ResultVO updatePass(@RequestBody AdminUserPassReq adminUserPassReq) {
        String token = adminUserService.updatePass(adminUserPassReq);
        return ResultVO.success(token);
    }

}
