package com.weiran.common.exception;


import com.weiran.common.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfoException extends RuntimeException {

    private String msg;

    public UserInfoException(String msg) {
        this.msg = msg;
    }

    public UserInfoException(ResponseEnum responseEnum) {
        this.msg = responseEnum.getMsg();
    }

}
