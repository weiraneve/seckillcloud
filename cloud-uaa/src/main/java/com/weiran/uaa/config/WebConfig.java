package com.weiran.uaa.config;

import com.weiran.uaa.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMVC 缺省配置
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    final LoginInterceptor loginInterceptor;

    /**
     * 注册拦截器
     *
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/goods/**", "/seckill/**", "/order/**")
                .excludePathPatterns("/login", "/login.html", "/register.html",
                       "/test", "/register", "/user/**", "/static/**");
    }

    /**
     * 配置静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
