package com.weiran.common.redis.manager;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 */
public interface DistributedLocker {

    /**
     * 加锁
     */
    void lock(String lockKey);

    /**
     * 解锁
     */
    void unlock(String lockKey);

    /**
     * 加锁
     */
    void lock(String lockKey, int timeout);

    /**
     * 解锁
     */
    void lock(String lockKey, TimeUnit unit, int timeout);
}
