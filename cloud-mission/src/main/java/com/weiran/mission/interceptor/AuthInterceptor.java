package com.weiran.mission.interceptor;

import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器，负责给前端服务器发送令牌和访问接口时验证令牌
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    final RedisService redisService;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String authInfo = request.getHeader("Authorization");  // 认证信息储存在前端服务器里，每次请求后端服务器接口则会在请求头header中带上
        if (!authInfo.startsWith("Bearer")) {
            // 认证失败，response返回403-状态码，让前端服务器重新登录
            response.setStatus(403);
            return false;
        }
        String loginToken = authInfo.split("Bearer ")[1]; // 只要Bearer 之后的部分
        Long userId;
        userId = redisService.get(UserKey.getById, loginToken, Long.class);
        if (userId == null) {
            response.setStatus(403);
            return false;
        }
        // 如果userId不为空，则重置登录token的有效时间，即调用expire命令，这里则取缔了过滤器的功能。
        redisService.expire(UserKey.getById , loginToken, RedisCacheTimeEnum.LOGIN_EXTIME.getValue());
        return true;
    }
}