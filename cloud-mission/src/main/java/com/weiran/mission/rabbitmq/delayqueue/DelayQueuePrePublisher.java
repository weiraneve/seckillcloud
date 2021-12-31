package com.weiran.mission.rabbitmq.delayqueue;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 延迟队列-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayQueuePrePublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendMsg(SeckillMessage seckillMessage) {
        try {
            // 设置延迟队列交换机、延迟队列路由键,消息实体并且发送消息
            //rabbitTemplate.convertAndSend(RabbitMqConstants.DELAY_EXCHANGE_PRE, RabbitMqConstants.DELAY_ROUTING_KEY_PRE, SeckillMessage);
            // 消息延迟时间设置为10秒,队列的x-message-ttl是设置30秒,以最小时间为准,即发送消息时的10秒
            rabbitTemplate.convertAndSend(RabbitMqConstants.DELAY_EXCHANGE_PRE, RabbitMqConstants.DELAY_ROUTING_KEY_PRE, seckillMessage, message -> {
                message.getMessageProperties().setExpiration("10000");
                return message;
            });
            log.info("延迟队列消息发送成功,消息：{},发送时间：{}", seckillMessage, LocalDateTime.now());
        } catch (Exception e) {
            log.error("延迟队列消息发送异常,消息：{},异常e：", seckillMessage, e);
        }
    }
}
