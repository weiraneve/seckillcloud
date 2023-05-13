package com.weiran.uaa.interceptor;

import cn.hutool.json.JSONUtil;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.AccessKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.uaa.annotations.AccessLimit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


/**
 * 拦截器，在规定时间内限制同一IP访问接口的次数
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccessInterceptor implements HandlerInterceptor {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    final RedisService redisService;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {

        // 获取调用 获取主要方法
        if (handler instanceof HandlerMethod) {
//            log.info("打印拦截方法handler ：{} ", handler);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 这一套流程是：先定义注解，然后在想使用的方法上加上注解，然后在拦截器或者处理器这里这样写。
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            // 从用到方法上注解取值
            int timeout = accessLimit.timeout();
            int limit = accessLimit.limit();
            // 获得客户访问的IP
            String ip = request.getRemoteAddr();
            AccessKey accessKey = AccessKey.withExpire;
            // 把redis对应access的key前缀和请求url和整数int的类信息去redis取出相应的对象
            Integer count = redisService.get(accessKey, ip, Integer.class);
            // 这里是，对于打上限流注解的方法，当限制小于maxCount时，redis里的对应的key的value值加1，如果大于等于maxCount，则由这里的拦截器直接返回访问繁忙信息，进行拦截。
            if (count  == null) {
                redisService.set(accessKey, ip, 1, timeout);
            } else if (count < limit) {
                redisService.increase(accessKey, ip);
            } else {
                log.info("用户IP{}，访问太频繁!", ip);
                render(response);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        OutputStream out = response.getOutputStream();
        String str = JSONUtil.toJsonStr(Result.fail(ResponseEnum.ACCESS_LIMIT_REACHED));
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
