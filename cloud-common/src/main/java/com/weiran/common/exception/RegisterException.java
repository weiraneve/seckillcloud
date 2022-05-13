package com.weiran.common.exception;

import com.weiran.common.enums.CodeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterException extends RuntimeException {

    private CodeMsg codeMsg;

    public RegisterException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
