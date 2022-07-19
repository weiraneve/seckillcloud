package com.weiran.common.exception;

import com.weiran.common.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private ResponseEnum responseEnum;

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.responseEnum = responseEnum;
    }

}
