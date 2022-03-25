//package com.weiran.common.redis.manager;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//
///**
// * Redisson分布式锁
// */
//public class RedisDistributedLocker implements DistributedLocker {
//
//    @Resource
//    RedissonClient redissonClient;
//
//    public void lock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock();
//    }
//
//    public void unlock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.unlock();
//    }
//
//    public void lock(String lockKey, int leaseTime) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(leaseTime, TimeUnit.SECONDS);
//    }
//
//
//    public void lock(String lockKey, TimeUnit unit ,int timeout) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(timeout, unit);
//    }
//
//}
