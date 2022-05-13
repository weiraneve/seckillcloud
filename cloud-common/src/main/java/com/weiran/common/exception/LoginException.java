package com.weiran.common.exception;


import com.weiran.common.enums.CodeMsg;
import com.weiran.common.obj.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class LoginException extends RuntimeException {

    private String msg;

    public LoginException(Result userResult, String mobile) {
        if (userResult.getCode() == CodeMsg.No_SIFT_PASS.getCode()) {
            log.info("{} 号码客户初筛未通过", mobile);
        } else if (userResult.getCode() == CodeMsg.PASSWORD_ERROR.getCode()) {
            log.info("{} 号码登录失败，密码错误" , mobile);
        } else if (userResult.getCode() == CodeMsg.MOBILE_NOT_EXIST.getCode()) {
            log.info("{} 号码登录，无此手机号", mobile);
        }
        this.msg = userResult.getMsg();
    }

}
