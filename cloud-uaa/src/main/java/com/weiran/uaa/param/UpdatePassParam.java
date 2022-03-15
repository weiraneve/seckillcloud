package com.weiran.uaa.param;

import lombok.Data;

/**
 * 更换密码字段
 */
@Data
public class UpdatePassParam {

    // 旧密码
    private String oldPassword;

    // 新密码
    private String newPassword;

}
