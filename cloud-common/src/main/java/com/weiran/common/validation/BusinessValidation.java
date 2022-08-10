package com.weiran.common.validation;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BusinessException;

public class BusinessValidation {

    /**
     * 抛出异常
     */
    public static void invalid(ResponseEnum responseEnum) {
        throw new BusinessException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常
     */
    public static void isInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new BusinessException(responseEnum);
        }
    }

}
