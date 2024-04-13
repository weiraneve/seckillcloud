package com.weiran.manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义用户登录失败
 */
public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        Result<ResponseEnum> httpResultVO;
        HttpStatus status = HttpStatus.OK;

        if (exception instanceof BadCredentialsException) {
            httpResultVO = Result.fail(ResponseEnum.USER_PASSWORD_VALID);
        } else if (exception instanceof NonceExpiredException) {
            status = HttpStatus.UNAUTHORIZED;
            httpResultVO = Result.fail(ResponseEnum.UNAUTHORIZED);
        } else if (exception instanceof InsufficientAuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
            httpResultVO = Result.fail(ResponseEnum.UNAUTHORIZED);
        } else if (exception instanceof DisabledException) {
            httpResultVO = Result.fail(ResponseEnum.USER_IS_BAN_FOUND);
        } else {
            httpResultVO = Result.fail(ResponseEnum.USER_NOT_FOUND);
        }

        sendResponse(response, status, httpResultVO);
    }

    private void sendResponse(HttpServletResponse response, HttpStatus status, Result<ResponseEnum> httpResultVO) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(httpResultVO));
    }
}
