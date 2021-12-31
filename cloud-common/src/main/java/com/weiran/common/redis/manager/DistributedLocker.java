package com.weiran.common.redis.manager;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 */
public interface DistributedLocker {

    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit, int timeout);
}
