package com.weiran.mission.service.impl;

import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import com.weiran.common.redis.key.*;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.utils.CookieUtil;
import com.weiran.common.utils.MD5Util;
import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.SeckillOrder;
import com.weiran.mission.entity.User;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.rabbitmq.BasicPublisher;
import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.service.SeckillOrderService;
import com.weiran.mission.service.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    final RedisService redisService;
    final SeckillGoodsManager seckillGoodsManager;
    final SeckillOrderService seckillOrderService;
    final BasicPublisher basicPublisher;
    final GoodsService goodsService;

    // 内存标记，减少redis访问
    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * 系统初始化，把秒杀信息加载到Redis缓存中。库存预热。
     */
    @PostConstruct
    public void initSeckillGoods() {
        List<GoodsBo> goodsBoList = goodsService.selectAllGoods();
        if (goodsBoList == null) {
            return;
        }
        for (GoodsBo goodsBo : goodsBoList) {
            // 加载秒杀商品的剩余数量
            redisService.set(SeckillKey.seckillCount, "" + goodsBo.getId(), goodsBo.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            // 加载秒杀商品的详情信息
            redisService.set(GoodsBoKey.goodsKey, "" + goodsBo.getId(), goodsBo, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
            localOverMap.put(goodsBo.getId(), true);
        }
    }

    // 执行秒杀
    @Override
    public Result<Integer> doSeckill(long goodsId, String path, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        // 验证path
        boolean check = checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        // 若为非，则为商品已经售完
        boolean over = localOverMap.get(goodsId);
        if (!over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 查询剩余数量
        int stock = redisService.get(SeckillKey.seckillCount, "" + goodsId, Integer.class);
        if (stock <= 0) {
            localOverMap.put(goodsId, false);
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 判断是否已经秒杀到了, 防止重复秒杀
        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId() , goodsId);
        if (seckillOrder != null) {
            return Result.error(CodeMsg.REPEATED_SECKILL);
        }
        // 预减库存
        redisService.decrease(SeckillKey.seckillCount, "" + goodsId);
        // 入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        // 判断库存、判断是否已经秒杀到了和减库存 下订单 写入秒杀订单都由RabbitMQ来执行，做到削峰填谷
        basicPublisher.sendMsg(seckillMessage);
        return Result.success(0); // 排队中
    }

    /**
     * 客户端轮询查询是否下单成功
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @Override
    public Result<Long> seckillResult(long goodsId, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = redisService.get(UserKey.getByName, loginToken, User.class);
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        long result;
        // 查寻秒杀订单
        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId() , goodsId);
        if (seckillOrder != null) { // 秒杀成功
            result = seckillOrder.getOrderId();
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
        User user;
        String loginToken = CookieUtil.readLoginToken(request);
        user = redisService.get(UserKey.getByName, loginToken, User.class);
        if (user == null) {
            return Result.error(CodeMsg.USER_NO_LOGIN);
        }
        String path = createSeckillPath(user, goodsId);
        return Result.success(path);
    }

    // 在redis里验证path
    private boolean checkPath(User user, long goodsId, String path) {
        if (user == null || path == null) {
            return false;
        }
        String redis_path = redisService.get(SeckillKey.getSeckillPath, "" + user.getId() + "_"+ goodsId, String.class);
        return path.equals(redis_path);
    }

    // 加盐生成唯一path，构成URl动态化
    private String createSeckillPath(User user, long goodsId) {
        if (user == null || goodsId <= 0) {
            return null;
        }
        // 随机返回一个唯一的id，加上123456的盐，然后md5加密
        String str = MD5Util.md5(UUID.randomUUID() + "123456");
        redisService.set(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, str, RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
        return str;
    }

    // 查看秒杀商品是否已经结束
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, "" + goodsId);
    }
}
