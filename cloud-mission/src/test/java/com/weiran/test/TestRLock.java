package com.weiran.test;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Redisson 尝试
 */
@SpringBootTest
public class TestRLock {

    public static void main(String[] args) {
        // 通过配置获取RedissonClient客户端的实例，然后getLock获取锁的实例
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.useSingleServer().setPassword("123456");

        final RedissonClient client = Redisson.create(config);
        RLock rLock = client.getLock("lock1");
        try {
            rLock.lock();
        } finally {
            rLock.unlock();
        }
    }

}
