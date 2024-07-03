package com.weiran.manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义被拒绝
 */
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    public static final String UTF_8 = "UTF-8";
    public static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding(UTF_8);
        response.setContentType(APPLICATION_JSON_UTF_8);
        Result<ResponseEnum> httpResultVO = Result.fail(ResponseEnum.FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResultVO));
    }
}
