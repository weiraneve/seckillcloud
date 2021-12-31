package com.weiran.common.redis.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * Redis分布式锁
 */
@Service
@RequiredArgsConstructor
public class RedisLock {

    final JedisPool jedisPool;

    private String LOCK_KEY = "redis_lock"; // 锁键

    protected long internalLockLeaseTime = 30000; // 锁过期时间

    private long TIMEOUT = 999999; // 获取锁的超时时间

    // SET命令的参数
    SetParams params = SetParams.setParams().nx().px(internalLockLeaseTime);

    /**
     * 加锁
     */
    public boolean lock(String id) {
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();
        try {
            for (;;) {
                // SET命令返回OK ，则证明获取锁成功
                String lock = jedis.set(LOCK_KEY, id, params);
                if ("OK".equals(lock)) {
                    return true;
                }
                // 否则循环等待，在timeout时间内仍未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l >= TIMEOUT) {
                    return false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * 解锁
     */
    public boolean unlock(String id) {
        Jedis jedis = jedisPool.getResource();
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";
        try {
            Object result = jedis.eval(script, Collections.singletonList(LOCK_KEY),
                    Collections.singletonList(id));
            if ("1".equals(result.toString())) {
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }
    }
}