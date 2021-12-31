package com.weiran.uaa.service;

import com.weiran.common.obj.Result;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     * 登陆
     */
    Result doLogin(HttpServletResponse response, HttpSession session, LoginParam loginParam);

    /**
     * 注销
     */
    Result doLogout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册
     */
    Result doRegister(RegisterParam registerParam);

    /**
     * 后台查询本次成功参与活动的用户信息
     */
    Result inquiryUser(long userId);
}