package com.weiran.common.exception;


import com.weiran.common.enums.ResponseEnum;

public class UserInfoException extends BaseCustomizeException {

    public UserInfoException(ResponseEnum responseEnum) {
        super(responseEnum);
    }
}
