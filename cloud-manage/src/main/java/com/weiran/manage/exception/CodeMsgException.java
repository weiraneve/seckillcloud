package com.weiran.manage.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class CodeMsgException extends RuntimeException {

    private Integer code;
    private String msg;

    public CodeMsgException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
