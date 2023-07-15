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

    private static final String STOCK_SCRIPT = "local stock = tonumber(redis.call('get',KEYS[1]));" +
            "if (stock <= 0) then" +
            "    return -1;" +
            "    end;" +
            "if (stock > 0) then" +
            "    return redis.call('incrby', KEYS[1], -1);" +
            "    end;";

    private static final String COUNT_SCRIPT =
            "local num=tonumber(redis.call('get',KEYS[1]));" +
                    "return num;";

    private static final String ADD_SCRIPT =
            "local num=redis.call('incr',KEYS[1]) return num";

    final RedisTemplate<String, Object> redisTemplate;

    public Long executeScript(String script, String key) {
        try {
            DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            return redisTemplate.execute(redisScript, Collections.singletonList(key));
        } catch (Exception e) {
            log.error("Script execution failed: " + script);
            log.error(e.toString());
            return null;
        }
    }

    public Long judgeStockAndDecrStock(Long goodsId) {
        return executeScript(STOCK_SCRIPT, RedisConstant.SECKILL_KEY + goodsId);
    }

    public Long getVisitorCount(String lockKey) {
        return executeScript(COUNT_SCRIPT, lockKey);
    }

    public void addVisitorCount(String lockKey) {
        executeScript(ADD_SCRIPT, lockKey);
    }
}
