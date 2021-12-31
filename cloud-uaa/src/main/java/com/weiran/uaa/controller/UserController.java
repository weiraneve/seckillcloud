package com.weiran.uaa.controller;

import com.weiran.common.obj.Result;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登陆控制器
 */
@Controller
@RequiredArgsConstructor
@Api("登陆控制器")
public class UserController {

    final UserService userService;

    @PostMapping("user/doLogin")
    @ResponseBody
    @ApiOperation("登陆，信息写进redis")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "响应", required = true),
            @ApiImplicitParam(value = "会话", required = true),
            @ApiImplicitParam(value = "登陆传递字段", required = true)
    })
    public Result doLogin(HttpServletResponse response, HttpSession session , LoginParam loginParam) {
        return userService.doLogin(response, session, loginParam);
    }

    @PostMapping("user/doRegister")
    @ResponseBody
    @ApiOperation("注册")
    @ApiImplicitParam(value = "注册传递字段", required = true)
    public Result doRegister(RegisterParam registerParam) {
        return userService.doRegister(registerParam);
    }

    @GetMapping("/register")
    public String register() {
        return "/register.html";
    }

    @GetMapping("user/logout")
    public Result doLogout(HttpServletRequest request, HttpServletResponse response) {
        return userService.doLogout(request, response);
    }

    @GetMapping("/login")
    public String login() {
        return "/login.html";
    }

    @ApiOperation("后台查询本次成功参与活动的用户信息")
    @RequestMapping(value = "/user/inquiryUser")
    @ResponseBody
    public Result inquiryUser(@RequestParam("userId") long userId) {
        return userService.inquiryUser(userId);
    }
}
