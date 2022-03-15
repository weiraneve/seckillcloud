package com.weiran.uaa.service;

import com.weiran.common.obj.Result;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;

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
     * 更换密码
     */
    Result updatePass(UpdatePassParam updatePassParam, HttpServletRequest request);

}