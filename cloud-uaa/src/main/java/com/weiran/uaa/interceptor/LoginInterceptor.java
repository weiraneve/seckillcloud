package com.weiran.uaa.interceptor;

import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.CookieUtil;
import com.weiran.uaa.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    final RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (loginToken != null) {
            user = redisService.get(UserKey.getByName, loginToken, User.class);
        }
        if (user == null) {
            // 未登陆，则重定向。
            response.sendRedirect("http://localhost:8205/uaa/login");
            return false;
        } else {
            // 如果user不为空，则重置session的时间，即调用expire命令，这里则取缔了过滤器的功能。
            redisService.expire(UserKey.getByName , loginToken, RedisCacheTimeEnum.REDIS_SESSION_EXTIME.getValue());
        }
        return true;
    }
}
