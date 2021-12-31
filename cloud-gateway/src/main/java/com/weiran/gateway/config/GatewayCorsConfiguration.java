package com.weiran.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * gateway 解决跨域
 * SaTokenConfigure中配置了全局跨域
 */
@Configuration
public class GatewayCorsConfiguration {

    private final Long MAX_AGE = 18000L;

    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许访问的头信息,*表示全部
        corsConfiguration.addAllowedHeader("*");
        // 允许提交请求的方法类型，*表示全部允许
        corsConfiguration.addAllowedMethod("*");
        // 允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        corsConfiguration.addAllowedOrigin("*");
        //这里一定要设置，因为这里要携带请求头进行凭证认证,允许cookies跨域
        corsConfiguration.setAllowCredentials(true);
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setMaxAge(MAX_AGE);
        //配置前端js允许访问的自定义响应头，不能用*
        corsConfiguration.addExposedHeader(HttpHeaders.ACCEPT);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
}
