package com.weiran.mission.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 配合拦截器使用的注解。用户访问秒杀接口的同时，把访问次数写到缓存中，在加上一个有效期。
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface SeckillLimit {

    // 网络上有些写法会有验证登录，在接口上使用来验证是否需要登录，
    // 这里项目中统一用登录拦截器验证登录，所以取消来这里对应功能。

    /**
     * 限制的有效期，写入Redis的过期时间。
     */
    int seconds();

    /**
     * 限制的最大次数
     */
    int maxCount();
}