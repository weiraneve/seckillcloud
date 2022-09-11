package com.weiran.manage.config;


import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;

/**
 * Feign日志打印
 * Feign提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解Feign中Http请求的细节。
 */
@Configuration
@RequiredArgsConstructor
public class FeignInterceptor implements RequestInterceptor {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    // 日志打印功能
    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }

    // 可以拦截feign内部请求
    @Override
    public void apply(RequestTemplate requestTemplate) {

    }
}
