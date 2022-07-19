package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;

public class SeckillException extends BaseCustomizeException {

    public SeckillException(ResponseEnum responseEnum) {
        super(responseEnum);
    }
}
