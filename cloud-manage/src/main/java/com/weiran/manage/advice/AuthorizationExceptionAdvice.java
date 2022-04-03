package com.weiran.manage.advice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AuthorizationExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(JWTDecodeException.class)
    public Result nonceExpiredException(JWTDecodeException exception) {
        return Result.error(ResponseEnum.TOKEN_PARSING_ERROR);
    }

}