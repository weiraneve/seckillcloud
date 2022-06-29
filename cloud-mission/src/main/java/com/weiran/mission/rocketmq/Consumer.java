package com.weiran.mission.rocketmq;

import cn.hutool.json.JSONUtil;
import com.weiran.common.enums.CodeMsg;
import com.weiran.common.exception.SeckillException;
import com.weiran.mission.pojo.entity.Order;
import com.weiran.mission.manager.OrderManager;
import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.service.SeckillGoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 消费者类
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "seckill-topic", consumerGroup = "seckill-consumer-group",
        selectorExpression = "tag1", selectorType = SelectorType.TAG,
        messageModel = MessageModel.CLUSTERING, consumeMode = ConsumeMode.CONCURRENTLY)
public class Consumer implements RocketMQListener<SeckillMessage> {

    final OrderManager orderManager;
    final SeckillGoodsService seckillGoodsService;

    @Override
    public void onMessage(SeckillMessage seckillMessage) {
        log.info("rocketmq 消费者接收消息: {}", JSONUtil.toJsonStr(seckillMessage));
        long userId = seckillMessage.getUserId();
        long goodsId = seckillMessage.getGoodsId();
        // 减库存，下订单，写入订单表
        Order order = new Order();
        // 幂等机制创建订单id
        Long orderId = goodsId * 1000000 + userId;
        order.setId(orderId);
        order.setUserId(userId);
        order.setGoodsId(goodsId);
        try {
            boolean flag = orderManager.save(order);
            if (!flag) {
                log.warn("写入订单表失败: {}", JSONUtil.toJsonStr(seckillMessage));
                throw new SeckillException(CodeMsg.ORDER_WRITE_ERROR);
            }
            log.info("成功写入订单表: {}", JSONUtil.toJsonStr(seckillMessage));
            seckillGoodsService.reduceStock(goodsId);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            // 违反数据库唯一约束条件
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                log.warn("{}订单写入异步操作问题", orderId);
            } else {
                e.printStackTrace();
            }
        }
    }
}

