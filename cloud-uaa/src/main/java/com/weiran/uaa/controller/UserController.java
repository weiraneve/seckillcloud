package com.weiran.uaa.controller;

import com.weiran.common.enums.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.uaa.annotations.AccessLimit;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;
import com.weiran.uaa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 */
@RestController
@RequiredArgsConstructor
@Api("登录控制器")
public class UserController {

    final UserService userService;

    @AccessLimit() // 默认限制1s内三次
    @PostMapping("user/doLogin")
    @ApiOperation("登录，信息写进redis")
    @ApiImplicitParam(value = "登录传递字段")
    public Result<String> doLogin(@RequestBody LoginParam loginParam) {
        return userService.doLogin(loginParam);
    }

    @PostMapping("user/doRegister")
    @ApiOperation("注册")
    @ApiImplicitParam(value = "注册传递字段")
    public Result<CodeMsg> doRegister(@RequestBody RegisterParam registerParam) {
        return userService.doRegister(registerParam);
    }

    @PostMapping("user/updatePass")
    @ApiOperation("更换密码")
    public Result<CodeMsg> updatePass(@RequestBody UpdatePassParam updatePassParam, HttpServletRequest request) {
        return userService.updatePass(updatePassParam, request);
    }

    @RequestMapping("user/logout")
    @ApiOperation("注销")
    public Result<CodeMsg> doLogout(HttpServletRequest request) {
        return userService.doLogout(request);
    }

}
