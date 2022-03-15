package com.weiran.mission.rocketmq;

import cn.hutool.json.JSONUtil;
import com.weiran.mission.entity.Order;
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
        try {
            log.info("rocketmq 消费者接收消息: {}", JSONUtil.toJsonStr(seckillMessage));
            long userId = seckillMessage.getUserId();
            long goodsId = seckillMessage.getGoodsId();
            // 减库存，下订单，写入订单表
            Order order = new Order();
            order.setUserId(userId);
            order.setGoodsId(goodsId);
            boolean flag = orderManager.save(order);
            if (!flag) {
                log.warn("写入订单表失败: {}", JSONUtil.toJsonStr(seckillMessage));
                return;
            }
            log.info("成功写入订单表: {}", JSONUtil.toJsonStr(seckillMessage));
            seckillGoodsService.reduceStock(goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

