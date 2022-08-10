package com.weiran.common.validation;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.SeckillException;

public class SeckillValidation {

    /**
     * 抛出异常
     */
    public static void invalid(ResponseEnum responseEnum) {
        throw new SeckillException(responseEnum);
    }

    /**
     * 表达式为真即抛出异常
     */
    public static void isInvalid(boolean expression, ResponseEnum responseEnum) {
        if (expression) {
            throw new SeckillException(responseEnum);
        }
    }

}
