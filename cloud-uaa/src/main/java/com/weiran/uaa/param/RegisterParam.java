package com.weiran.uaa.param;

import lombok.Data;

/**
 * 注册字段
 */
@Data
public class RegisterParam {

    // 手机号
    private String mobile;

    // 密码
    private String password;
    
    // 身份证号码
    private String identityCardId;

    // 用户姓名
    private String username;

}
