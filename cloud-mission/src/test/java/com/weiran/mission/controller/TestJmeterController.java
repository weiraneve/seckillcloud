package com.weiran.mission.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.key.SeckillKey;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.SM3Util;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.pojo.entity.Order;
import com.weiran.mission.pojo.entity.SeckillGoods;
import com.weiran.mission.pojo.entity.User;
import com.weiran.mission.rabbitmq.BasicPublisher;
import com.weiran.mission.rabbitmq.SeckillMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Jmeter测试
 */
@Controller
@RequiredArgsConstructor
public class TestJmeterController {

    final RedisService redisService;
    final OrderManager orderManager;
    final SeckillGoodsManager seckillGoodsManager;
    final BasicPublisher messageSender;
    private final RedisLua redisLua;
    // 内存标记，减少redis访问
    private final HashMap<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 系统初始化，把秒杀商品库存剩余加载到Redis缓存中。库存预热。
     */
    @PostConstruct
    public void initSeckillGoodsNumber() {
        List<SeckillGoods> seckillGoodsList = seckillGoodsManager.list();
        if (seckillGoodsList == null) {
            return;
        }
        for (SeckillGoods seckillGoods : seckillGoodsList) {
            // 用商品Id作为key，加载秒杀商品的剩余数量
            redisService.set(SeckillGoodsKey.seckillCount, String.valueOf(seckillGoods.getGoodsId()), seckillGoods.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            if (seckillGoods.getStockCount() > 0) {
                localOverMap.put(seckillGoods.getGoodsId(), true);
            } else {
                localOverMap.put(seckillGoods.getGoodsId(), false);
            }
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    public Result<Integer> test(@RequestParam("id") long id) {
        // 默认传入id为1
        return doTest(id);
    }

    private Result<Integer> doTest(long id) {
        User user = new User();
        user.setId(id);
        Long userId = user.getId();
        String userName = "user" + id;
        user.setUserName(userName);
        redisService.set(UserKey.getById, userName, userId, 120);
        String path = getSeckillPath(userName);
        return doSeckill(userId, path);
    }

    private String getSeckillPath(String token) {
        Long userId = redisService.get(UserKey.getById, token, Long.class);
        if (userId == null) {
            return null;
        }
        return createSeckillPath(userId);
    }

    // 进行秒杀
    Result<Integer> doSeckill(Long userId, String path) {
        // 验证path
        boolean check = checkPath(userId, path);
        if (!check) {
            return Result.fail(ResponseEnum.REQUEST_ILLEGAL);
        }
        // 若为非，则为商品已经售完
        boolean over = localOverMap.get((long) 1);
        if (!over) {
            return Result.fail(ResponseEnum.SECKILL_OVER);
        }
        // 使用幂等机制，根据用户和商品id生成订单号，防止重复秒杀
        Long orderId = (long) 1000000 + userId;
        Order order = orderManager.getOne(Wrappers.<Order>lambdaQuery()
                .eq(Order::getId, orderId));
        if (order != null) {
            return Result.fail(ResponseEnum.REPEATED_SECKILL);
        }
        Long count = redisLua.judgeStockAndDecrStock((long) 1);
        if (count == -1) {
            localOverMap.put((long) 1, false);
            return Result.fail(ResponseEnum.SECKILL_OVER);
        }

        // 入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUserId(userId);
        seckillMessage.setGoodsId(1);
        // 判断库存、判断是否已经秒杀到了和减库存 下订单 写入订单都由消息队列来执行，做到削峰填谷
        messageSender.sendMsg(seckillMessage);

        return Result.success(0); // 排队中
    }

    // 加盐生成唯一path，构成URl动态化
    private String createSeckillPath(Long userId) {
        if (userId == null) {
            return null;
        }
        // 随机返回一个唯一的id，加上123456的盐，然后sm3加密
        String str = SM3Util.sm3(UUID.randomUUID() + "123456");
        redisService.set(SeckillKey.getSeckillPath, userId + "_" + (long) 1, str, RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
        return str;
    }

    // 在redis里验证path
    private boolean checkPath(Long userId, String path) {
        if (userId == null || path == null) {
            return false;
        }
        String redis_path = redisService.get(SeckillKey.getSeckillPath, userId + "_" + (long) 1, String.class);
        return path.equals(redis_path);
    }

}
