package com.weiran.common.exception.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weiran.common.enums.CodeMsg;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.CustomizeException;
import com.weiran.common.exception.LoginException;
import com.weiran.common.exception.RegisterException;
import com.weiran.common.exception.SeckillException;
import com.weiran.common.obj.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<String> handle(LoginException exception) {
        return Result.error(exception.getMsg());
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<CodeMsg> handle(RegisterException exception) {
        return Result.error(exception.getCodeMsg());
    }

    @ExceptionHandler(SeckillException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<CodeMsg> handle(SeckillException exception) {
        return Result.error(exception.getCodeMsg());
    }

    @ExceptionHandler(CustomizeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Object> handle(CustomizeException exception) {
        return Result.error(exception.getResponseEnum());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(JWTDecodeException.class)
    public Result<Object> handle() {
        return Result.error(ResponseEnum.TOKEN_PARSING_ERROR);
    }


}
