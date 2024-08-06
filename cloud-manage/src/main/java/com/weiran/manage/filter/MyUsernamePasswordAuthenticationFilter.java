package com.weiran.manage.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URL = "/login";
    private static final String HTTP_METHOD = "POST";
    private static final String AUTH_MANAGER_NOT_NULL_MESSAGE = "authenticationManager must be specified";
    private static final String SUCCESS_HANDLER_NOT_NULL_MESSAGE = "AuthenticationSuccessHandler must be specified";
    private static final String FAILURE_HANDLER_NOT_NULL_MESSAGE = "AuthenticationFailureHandler must be specified";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";

    public MyUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher(LOGIN_URL, HTTP_METHOD));
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getAuthenticationManager(), AUTH_MANAGER_NOT_NULL_MESSAGE);
        Assert.notNull(getSuccessHandler(), SUCCESS_HANDLER_NOT_NULL_MESSAGE);
        Assert.notNull(getFailureHandler(), FAILURE_HANDLER_NOT_NULL_MESSAGE);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String body = StreamUtils.copyToString(request.getInputStream(), DEFAULT_CHARSET);
        String username = null, password = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            username = jsonObj.getString(USERNAME_FIELD);
            password = jsonObj.getString(PASSWORD_FIELD);
        }

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
