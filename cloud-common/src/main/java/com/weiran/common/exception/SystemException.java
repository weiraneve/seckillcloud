package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;

public class SystemException extends BaseCustomizeException {

    public SystemException(ResponseEnum responseEnum) {
        super(responseEnum);
    }
}
