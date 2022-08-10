package com.weiran.common.validation;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.UserInfoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfoValidation {

    /**
     * 抛出异常(默认错误1000)
     */
    public static void invalid(ResponseEnum responseEnum) {
        throw new UserInfoException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常(默认错误1000)
     */
    public static void isInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new UserInfoException(responseEnum);
        }
    }

    /**
     * 表达式为真即抛出异常(默认错误1000)
     */
    public static void isInvalidAndMobile(boolean expression, int resultCode, String mobile) {
        if (expression) {
            if (resultCode == ResponseEnum.PASSWORD_ERROR.getCode()) {
                log.info("{} 号码登录失败，密码错误" , mobile);
                throw new UserInfoException(ResponseEnum.PASSWORD_ERROR);
            } else if (resultCode == ResponseEnum.MOBILE_NOT_EXIST.getCode()) {
                log.info("{} 号码登录，无此手机号", mobile);
                throw new UserInfoException(ResponseEnum.MOBILE_NOT_EXIST);
            }
        }
    }

}
