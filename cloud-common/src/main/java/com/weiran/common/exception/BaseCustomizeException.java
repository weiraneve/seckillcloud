package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class BaseCustomizeException extends RuntimeException {

    private final ResponseEnum responseEnum;

    public BaseCustomizeException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

}
