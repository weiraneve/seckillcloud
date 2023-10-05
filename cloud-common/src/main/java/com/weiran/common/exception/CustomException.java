package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;

public class CustomException extends BaseCustomizeException {

    public CustomException(ResponseEnum responseEnum) {
        super(responseEnum);
    }

}
