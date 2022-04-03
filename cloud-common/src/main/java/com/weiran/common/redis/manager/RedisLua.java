package com.weiran.common.redis.manager;

import com.weiran.common.enums.RedisConstant;
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
     * 判断库存和预减库存
     */
    public Long judgeStockAndDecrStock(Long goodsId) {
        String stockScript = "local stock = tonumber(redis.call('get',KEYS[1]));" +
                "if (stock <= 0) then" +
                "    return -1;" +
                "    end;" +
                "if (stock > 0) then" +
                "    return redis.call('incrby', KEYS[1], -1);" +
                "    end;";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(stockScript, Long.class);
        Long count = redisTemplate.execute(redisScript, Collections.singletonList(RedisConstant.SECKILL_KEY + goodsId));
        return count;

    }

    /**
     * 统计访问次数
     */
    public Long getVisitorCount(String lockKey) {
        try {
            // LUA脚本中 tonumber函数可将调用结果转换为number形式
            String countScript =
                    "local num=tonumber(redis.call('get',KEYS[1]));" +
                            "return num;";
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(countScript, Long.class);
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
