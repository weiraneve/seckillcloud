package com.weiran.uaa.config;

import com.weiran.uaa.interceptor.AccessInterceptor;
import com.weiran.uaa.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMVC 缺省配置
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    final AuthInterceptor authInterceptor;
    final AccessInterceptor accessInterceptor;

    /**
     * 注册拦截器
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/goods/**", "/seckill/**", "/order/**")
                .excludePathPatterns("/login", "/test", "/register", "/user/**", "/static/**");

        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/user/**");

    }

}
