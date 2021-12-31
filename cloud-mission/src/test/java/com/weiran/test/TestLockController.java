//package com.weiran.mission.test;
//
//import com.weiran.common.redis.manager.RedisLock;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.UUID;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Slf4j
//@Controller
//public class TestLockController {
//
//    @Autowired
//    RedisLock redisLock;
//
//    int count = 0;
//
//    @RequestMapping("test/index")
//    @ResponseBody
//    public String index() throws InterruptedException {
//        int clientCount = 100;
//        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
//        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < clientCount; i++){
//            executorService.execute(() -> {
//                // 获取唯一的ID字符串
//                String id = UUID.randomUUID() + "";
//                try {
//                    redisLock.lock(id);
//                    count ++;
//                } finally {
//                    redisLock.unlock(id);
//                }
//                countDownLatch.countDown();
//            });
//        }
//        countDownLatch.await();
//        long end = System.currentTimeMillis();
//        long endToStart = end - start;
//        log.info("执行线程数:{},总耗时:{},count数为:{}", clientCount, endToStart, count);
//        return "执行线程数:" + clientCount + ",总耗时:" + endToStart + ",count数为:" + count;
//    }
//}