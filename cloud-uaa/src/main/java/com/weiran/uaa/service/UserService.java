package com.weiran.uaa.service;

import com.weiran.common.obj.Result;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /**
     * 登陆
     * @return
     */
    Result doLogin(LoginParam loginParam);

    /**
     * 注销
     */
    Result doLogout(HttpServletRequest request);

    /**
     * 注册
     */
    Result doRegister(RegisterParam registerParam);

    /**
     * 后台查询本次成功参与活动的用户信息
     */
    Result inquiryUser(long userId);
}