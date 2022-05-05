package com.weiran.uaa.service;

import com.weiran.common.enums.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.uaa.param.LoginParam;
import com.weiran.uaa.param.RegisterParam;
import com.weiran.uaa.param.UpdatePassParam;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /**
     * 登录
     */
    Result<String> doLogin(LoginParam loginParam);

    /**
     * 注销
     */
    Result<CodeMsg> doLogout(HttpServletRequest request);

    /**
     * 注册
     */
    Result<CodeMsg> doRegister(RegisterParam registerParam);

    /**
     * 更换密码
     */
    Result<CodeMsg> updatePass(UpdatePassParam updatePassParam, HttpServletRequest request);

}