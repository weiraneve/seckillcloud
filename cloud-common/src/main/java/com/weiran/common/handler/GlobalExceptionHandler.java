package com.weiran.common.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BusinessException;
import com.weiran.common.exception.SeckillException;
import com.weiran.common.exception.SystemException;
import com.weiran.common.exception.UserInfoException;
import com.weiran.common.obj.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

// 全局异常处理器
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserInfoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handle(UserInfoException exception) {
        log.error(exception.getResponseEnum().getMsg());
        return Result.fail(exception.getResponseEnum());
    }

    @ExceptionHandler(SeckillException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<ResponseEnum> handle(SeckillException exception) {
        log.error(exception.getResponseEnum().getMsg());
        return Result.fail(exception.getResponseEnum());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Object> handle(BusinessException exception) {
        log.error(exception.getResponseEnum().getMsg());
        return Result.fail(exception.getResponseEnum());
    }

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handle(SystemException ex) {
        log.error(ex.getResponseEnum().getMsg());
        return Result.fail(ex.getResponseEnum());
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Object> handle() {
        log.error(ResponseEnum.TOKEN_PARSING_ERROR.getMsg());
        return Result.fail(ResponseEnum.TOKEN_PARSING_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handle(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        log.error(ex.getMessage());
        return Result.fail(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handle(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        log.error(ex.getMessage());
        return Result.fail(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handle(Exception ex) {
        log.error(ex.getMessage());
        return Result.fail(ex.getMessage());
    }

}
