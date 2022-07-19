package com.weiran.common.utils;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BusinessException;
import com.weiran.common.exception.SeckillException;
import com.weiran.common.exception.UserInfoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssertUtil {

    /**
     * 抛出异常(默认错误1000)
     */
    public static void userInfoInvalid(ResponseEnum responseEnum) {
        throw new UserInfoException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常(默认错误1000)
     */
    public static void userInfoInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new UserInfoException(responseEnum);
        }
    }

    /**
     * 表达式为真即抛出异常(默认错误1000)
     */
    public static void userInfoInvalid(boolean expression, int resultCode, String mobile, String resultMsg) {
        if (expression) {
            if (resultCode == ResponseEnum.PASSWORD_ERROR.getCode()) {
                log.info("{} 号码登录失败，密码错误" , mobile);
            } else if (resultCode == ResponseEnum.MOBILE_NOT_EXIST.getCode()) {
                log.info("{} 号码登录，无此手机号", mobile);
            }
            throw new UserInfoException(resultMsg);
        }
    }

    /**
     * 表达式为真即抛出异常
     */
    public static void businessInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 抛出异常
     */
    public static void businessInvalid(ResponseEnum responseEnum) {
        throw new BusinessException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常
     */
    public static void seckillInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new SeckillException(responseEnum);
        }
    }

    /**
     * 抛出异常
     */
    public static void seckillInvalid(ResponseEnum responseEnum) {
        throw new SeckillException(responseEnum);
    }

}
