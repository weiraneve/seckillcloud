package com.weiran.gateway.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 过滤器打印请求地址
 */
@Component
@Slf4j
public class GatewayFilterConfig implements GlobalFilter, Ordered {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        log.info("当前请求地址：{}", path);
        return chain.filter(exchange.mutate().request(
                exchange.getRequest()
                        .mutate()
                        .header("from", "public")
                        .build()).build());
    }

    /**
     * 拦截器的优先级，数字越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
