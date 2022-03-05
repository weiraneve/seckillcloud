package com.weiran.manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weiran.manage.enums.ResponseEnum;
import com.weiran.manage.response.ResultVO;
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
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        // 防止乱码
        response.setContentType("application/json;charset=UTF-8");
        ResultVO httpResultVO = ResultVO.fail(ResponseEnum.FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResultVO));
    }
}
