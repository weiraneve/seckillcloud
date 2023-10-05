package com.weiran.common.validation;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.CustomException;

public class CustomValidation {

    /**
     * 抛出异常
     */
    public static void invalid(ResponseEnum responseEnum) {
        throw new CustomException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常
     */
    public static void isInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new CustomException(responseEnum);
        }
    }

}
