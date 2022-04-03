package com.weiran.manage.advice;

import com.weiran.common.obj.Result;
import com.weiran.manage.exception.CustomizeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomizeExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CustomizeException.class)
    public Result nonceExpiredException(CustomizeException exception) {
        return Result.error(exception.getResponseEnum());
    }
}