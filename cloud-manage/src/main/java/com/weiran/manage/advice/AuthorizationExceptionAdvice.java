package com.weiran.manage.advice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.response.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AuthorizationExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(JWTDecodeException.class)
    public ResultVO nonceExpiredException(JWTDecodeException exception) {
        return ResultVO.fail(ResponseEnum.TOKEN_PARSING_ERROR);
    }

}
