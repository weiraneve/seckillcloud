package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SeckillException extends RuntimeException {

    private ResponseEnum responseEnum;

    public SeckillException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
}
