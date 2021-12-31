package com.weiran.common.redis.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * lua脚本使用
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLua {

    final JedisPool jedisPool;

    /**
     * 统计访问次数
     */
    public Object getVisitorCount(String key) {
        Object object;
        try {
            Jedis jedis = jedisPool.getResource();
            String count =
                    "local num=redis.call('get',KEYS[1]) return num";
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> list = new ArrayList<String>();
            jedis.auth("123456");
            String luaScript = jedis.scriptLoad(count);
            object = jedis.evalsha(luaScript, keys, list);
        } catch (Exception e) {
            log.error("统计访问次数失败！！！", e);
            return "0";
        }
        return object;
    }

    /**
     * 统计访问次数
     */
    public void visitorCount(String key) {
        try {
            Jedis jedis = jedisPool.getResource();
            String count =
                    "local num=redis.call('incr',KEYS[1]) return num";
            List<String> keys = new ArrayList<String>();
            keys.add(key);
            List<String> list = new ArrayList<String>();
            jedis.auth("123456");
            String luaScript = jedis.scriptLoad(count);
            jedis.evalsha(luaScript, keys, list);
        } catch (Exception e) {
            log.error("统计访问次数失败！！！", e);
        }
    }
}
