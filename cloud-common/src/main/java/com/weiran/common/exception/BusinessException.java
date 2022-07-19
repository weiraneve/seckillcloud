package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;

public class BusinessException extends BaseCustomizeException {

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum);
    }

}
