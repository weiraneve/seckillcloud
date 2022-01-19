package com.weiran.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.key.SeckillKey;
import com.weiran.common.redis.key.UserKey;
import com.weiran.common.redis.manager.RedisLua;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.MD5Util;
import com.weiran.mission.entity.Order;
import com.weiran.mission.entity.SeckillGoods;
import com.weiran.mission.entity.User;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.ackmodel.manual.ManualAckPublisher;
import com.weiran.mission.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
@RequiredArgsConstructor
public class TestJmeterController {

    final RedisService redisService;
    final GoodsService goodsService;
    final OrderManager orderManager;
    final SeckillGoodsManager seckillGoodsManager;
    final ManualAckPublisher manualAckPublisher;
    final RedisLua redisLua;

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
            }
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    public Result<Integer> test(@RequestParam("id") long id) {

        return doTest(id);
    }

    private Result<Integer> doTest(long id) {
        User user = new User();
        user.setId(id);
        Long userId = user.getId();
        String userName = "user" + id;
        user.setUserName(userName);
        String token = userName;
        redisService.set(UserKey.getById, token, userId, 120);
        String path = getSeckillPath(token, 1);
        if (path == null) {
            throw new RuntimeException("秒杀URL生成失败");
        }
        return doSeckill(userId, 1, path);
    }

    private String getSeckillPath(String token, long goodsId) {
        Long userId = redisService.get(UserKey.getById, token, Long.class);
        if (userId == null) {
            return null;
        }
        String path = createSeckillPath(userId, goodsId);
        return path;
    }

    // 进行秒杀
    private Result<Integer> doSeckill(Long userId, long goodsId, String path) {
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
        // 查询剩余数量
        int stock = redisService.get(SeckillGoodsKey.seckillCount, "" + goodsId, Integer.class);
        if (stock <= 0) {
            localOverMap.put(goodsId, false);
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 判断是否已经秒杀到了, 防止重复秒杀
        Order order = orderManager.getOne(Wrappers.<Order>lambdaQuery()
                .eq(Order::getUserId, userId)
                .eq(Order::getGoodsId, goodsId));
        if (order != null) {
            return Result.error(CodeMsg.REPEATED_SECKILL);
        }
        // 预减库存
        redisService.decrease(SeckillGoodsKey.seckillCount, "" + goodsId);

        // 入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUserId(userId);
        seckillMessage.setGoodsId(goodsId);
        // 判断库存、判断是否已经秒杀到了和减库存 下订单 写入订单都由RabbitMQ来执行，做到削峰填谷
        manualAckPublisher.sendMsg(seckillMessage); // 这里使用的多消费者实例，增加并发能力。使用BasicPublisher则是单一消费者实例

//        // 不用MQ
//        Goods goodsBo = goodsService.getGoodsBoByGoodsId(goodsId);
//        // 减库存 下订单 写入订单
//        seckillOrderService.insertByUserAndGoodsBo(userId, goodsBo);

        return Result.success(0); // 排队中
    }

    // 加盐生成唯一path，构成URl动态化
    private String createSeckillPath(Long userId, long goodsId) {
        if (userId == null || goodsId <= 0) {
            return null;
        }
        // 随机返回一个唯一的id，加上123456的盐，然后md5加密
        String str = MD5Util.md5(UUID.randomUUID() + "123456");
        redisService.set(SeckillKey.getSeckillPath, "" + userId + "_" + goodsId, str, RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
        return str;
    }

    // 在redis里验证path
    private boolean checkPath(Long userId, long goodsId, String path) {
        if (userId == null || path == null) {
            return false;
        }
        String redis_path = redisService.get(SeckillKey.getSeckillPath, "" + userId + "_"+ goodsId, String.class);
        return path.equals(redis_path);
    }

}
