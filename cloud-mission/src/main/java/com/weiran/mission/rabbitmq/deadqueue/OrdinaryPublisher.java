package com.weiran.mission.rabbitmq.deadqueue;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 普通队列-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrdinaryPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendMsg(SeckillMessage seckillMessage) {
        try {
            //设置交换机、路由键,发送消息
            rabbitTemplate.convertAndSend(RabbitMqConstants.DIRECT_EXCHANGE_DEAD_PRE, RabbitMqConstants.DIRECT_ROUTING_KEY_DEAD_PRE, seckillMessage);
            log.info("普通队列-生产者,发送消息：{}", seckillMessage);
        } catch (Exception e) {
            log.error("普通队列-生产者,发送消息异常,消息：{},异常：", seckillMessage, e);
        }
    }
}
