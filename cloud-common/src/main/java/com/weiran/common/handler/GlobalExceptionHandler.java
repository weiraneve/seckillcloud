package com.weiran.common.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BaseCustomizeException;
import com.weiran.common.exception.CustomException;
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

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleCustomExceptions(BaseCustomizeException ex) {
        logError(ex.getResponseEnum().getMsg(), ex);
        return Result.fail(ex.getResponseEnum());
    }

    @ExceptionHandler(JWTDecodeException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<Object> handleJWTException() {
        logError(ResponseEnum.TOKEN_PARSING_ERROR.getMsg());
        return Result.fail(ResponseEnum.TOKEN_PARSING_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleValidationExceptions(Exception ex) {
        String message;
        if (ex instanceof MethodArgumentNotValidException) {
            message = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(";"));
        } else {
            message = ((ConstraintViolationException) ex).getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(";"));
        }
        logError(message, ex);
        return Result.fail(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleGeneralException(Exception ex) {
        logError(ResponseEnum.SYSTEM_ERROR.getMsg(), ex);
        return Result.fail(ResponseEnum.SYSTEM_ERROR);
    }

    private void logError(String message, Exception ex) {
        log.error(message, ex);
    }

    private void logError(String message) {
        log.error(message);
    }
}

