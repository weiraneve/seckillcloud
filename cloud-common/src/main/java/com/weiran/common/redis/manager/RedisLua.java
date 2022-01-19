package com.weiran.common.redis.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * lua脚本使用
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLua {

    final RedisTemplate<String, Object> redisTemplate;

    /**
     * 统计访问次数
     */
    public Long getVisitorCount(String lockKey) {
        try {
            String countScript =
                    "local num=redis.call('get',KEYS[1]) return num";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(countScript, Long.class);
            // TODO 这里的LUA脚本的调用，尝试始终没有到达预期，第一是限制时间内的访问，第二是取不到值。LUA本身可以用来高并发限流。
            return redisTemplate.execute(redisScript, Collections.singletonList(lockKey));
        } catch (Exception e) {
            log.error("统计访问次数失败！！！", e);
            return null;
        }
    }

    /**
     * 增加访问次数
     */
    public void addVisitorCount(String lockKey) {
        try {
            String addScript =
                    "local num=redis.call('incr',KEYS[1]) return num";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(addScript, Long.class);
            // 限制60s访问5次
            redisTemplate.execute(redisScript, Collections.singletonList(lockKey));
        } catch (Exception e) {
            log.error("增加访问次数失败！！！", e);
        }
    }
}
