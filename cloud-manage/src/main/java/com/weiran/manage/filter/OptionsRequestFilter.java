package com.weiran.manage.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;


public class OptionsRequestFilter extends OncePerRequestFilter {

    private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private static final String ALLOWED_CREDENTIALS = "true";
    private static final String ALLOWED_METHODS = "OPTIONS,POST,GET,DELETE,PUT,PATCH";
    private static final String MAX_AGE = "3600";
    private static final String ALLOWED_HEADERS = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, Content-Language, Cache-Control, X-E4M-With";

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);
            response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
            response.setHeader(ACCESS_CONTROL_MAX_AGE, MAX_AGE);
            response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
