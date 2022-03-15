package com.weiran.mission.config;

import com.weiran.mission.interceptor.SeckillInterceptor;
import com.weiran.mission.interceptor.AuthInterceptor;
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

    final SeckillInterceptor seckillInterceptor;
    final AuthInterceptor authInterceptor;

    /**
     * 注册拦截器
     *
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(seckillInterceptor)
                .addPathPatterns("/seckill/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/goods/**", "/seckill/**", "/order/**")
                .excludePathPatterns("/test", "/static/**");
    }

    /**
     * 配置静态资源路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
