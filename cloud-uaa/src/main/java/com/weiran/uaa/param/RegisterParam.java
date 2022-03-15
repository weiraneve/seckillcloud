package com.weiran.uaa.param;

import lombok.Data;

/**
 * 注册字段
 */
@Data
public class RegisterParam {

    // 手机号
    private String registerMobile;

    // 用户名
    private String registerUsername;

    // 身份证号码
    private String registerIdentity;

    // 密码
    private String registerPassword;

}
