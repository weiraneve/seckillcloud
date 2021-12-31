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
 * discription: 过滤器打印请求地址
 */
@Component
@Slf4j
public class GatewayFilterConfig implements GlobalFilter, Ordered {

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        log.info("当前请求地址：{}", path);
        return chain.filter(exchange);
    }

//    @SneakyThrows
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String url_1 = "/Login";
//        String url = exchange.getRequest().getURI() + "";
//
//        if(url.contains(url_1)){
//            return chain.filter(exchange);
//        }
//
//        ServerHttpRequest request = exchange.getRequest();
//        String loginToken = CookieUtil.readLoginToken(request);
//        HttpHeaders headers = request.getHeaders();
//        String token = headers.getFirst(AUTHORIZE_TOKEN);
//        if (!stringRedisTemplate.hasKey(token)){
//            ServerHttpResponse response = exchange.getResponse();
//            JSONObject message = new JSONObject();
//            message.put("status", -1);
//            message.put("data", "鉴权失败");
//            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = response.bufferFactory().wrap(bits);
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            //指定编码，否则在浏览器中会中文乱码
//            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//            return response.writeWith(Mono.just(buffer));
//        }
//        return chain.filter(exchange);
//    }

    /**
     * 拦截器的优先级，数字越小优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
