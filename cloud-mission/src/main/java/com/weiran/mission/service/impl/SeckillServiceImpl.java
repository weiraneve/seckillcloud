package com.weiran.mission.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisConstant;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.*;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.SM3Util;
import com.weiran.mission.entity.Order;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rocketmq.MessageSender;
import com.weiran.mission.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    final RedisService redisService;
    final SeckillGoodsManager seckillGoodsManager;
    final OrderManager orderManager;
    final MessageSender messageSender;
    final RedisTemplate<String, Object> redisTemplate;

    // 内存标记，减少redis访问
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

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
            redisService.set(SeckillGoodsKey.seckillCount, "" + seckillGoods.getGoodsId(), seckillGoods.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            if (seckillGoods.getStockCount() > 0) {
                localOverMap.put(seckillGoods.getId(), true);
            } else {
                localOverMap.put(seckillGoods.getId(), false);
            }
        }
    }

    // 执行秒杀
    @Override
    @Transactional
    public Result<Integer> doSeckill(long goodsId, String path, HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1];
        long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        // 验证path
        boolean check = checkPath(userId, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        // 若为非，则为商品已经售完
        boolean over = localOverMap.get(goodsId);
        if (!over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 使用幂等机制，根据用户和商品id生成订单号，防止重复秒杀
        Long orderId  = goodsId * 1000000 + userId;
        Order order = orderManager.getOne(Wrappers.<Order>lambdaQuery()
                .eq(Order::getId, orderId));
        if (order != null) {
            return Result.error(CodeMsg.REPEATED_SECKILL);
        }
        // LUA脚本判断库存和预减库存
        String stockScript = "local stock = tonumber(redis.call('get',KEYS[1]));" +
                "if (stock <= 0) then" +
                "    return -1;" +
                "    end;" +
                "if (stock > 0) then" +
                "    return redis.call('incrby', KEYS[1], -1);" +
                "    end;";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(stockScript, Long.class);
        Long count = redisTemplate.execute(redisScript, Collections.singletonList(RedisConstant.SECKILL_KEY + goodsId));
        if (count == -1) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUserId(userId);
        seckillMessage.setGoodsId(goodsId);
        // 判断库存、判断是否已经秒杀到了和减库存 下订单 写入订单都由RabbitMQ来执行，做到削峰填谷
//        manualAckPublisher.sendMsg(seckillMessage); // 这里使用的多消费者实例，增加并发能力。使用BasicPublisher则是单一消费者实例
        messageSender.asyncSend(seckillMessage); // 这里使用RocketMQ
        return Result.success(0); // 排队中
    }

    // 客户端轮询查询是否下单成功
    @Override
    public Result<Long> seckillResult(long goodsId, HttpServletRequest request) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1];
        Long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        if (userId == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        long result; // orderId：成功，0：排队中，1：秒杀失败
        // 查寻订单
        Order order = orderManager.getOne(Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, userId)
                .eq(Order::getGoodsId, goodsId));
        if (order != null) { // 秒杀成功
            result = order.getId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                result = -1; // 秒杀失败
            } else {
                result = 0; // 排队中
            }
        }
        return Result.success(result);
    }

    // 返回一个唯一的path的id
    @Override
    public Result<String> getSeckillPath(HttpServletRequest request, long goodsId) {
        String authInfo = request.getHeader("Authorization");
        String loginToken = authInfo.split("Bearer ")[1];
        Long userId = redisService.get(UserKey.getById, loginToken, Long.class);
        if (userId == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        String path = createSeckillPath(userId, goodsId);
        return Result.success(path);
    }

    // 在redis里验证path
    private boolean checkPath(Long userId, long goodsId, String path) {
        if (userId == null || path == null) {
            return false;
        }
        String redis_path = redisService.get(SeckillKey.getSeckillPath, "" + userId + "_"+ goodsId, String.class);
        return path.equals(redis_path);
    }

    // 加盐生成唯一path，构成URl动态化
    private String createSeckillPath(Long userId, Long goodsId) {
        if (userId == null || goodsId == null) {
            return null;
        }
        // 随机返回一个唯一的id，加上123456的盐，然后sm3加密
        String str = SM3Util.sm3(UUID.randomUUID() + "123456");
        redisService.set(SeckillKey.getSeckillPath, "" + userId + "_" + goodsId, str, RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
        return str;
    }

    // 查看秒杀商品是否已经结束
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }
}
