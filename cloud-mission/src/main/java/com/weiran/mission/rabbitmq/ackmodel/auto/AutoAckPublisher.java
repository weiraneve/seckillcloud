package com.weiran.mission.rabbitmq.ackmodel.auto;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 确认消费模式为自动确认机制-AUTO,采用直连传输directExchange消息模型-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AutoAckPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendMsg(SeckillMessage seckillMessage) {
        if (seckillMessage != null) {
            try {
                // 设置交换机
                rabbitTemplate.setExchange(RabbitMqConstants.AUTO_ACKNOWLEDGE_EXCHANGE);
                // 设置路由
                rabbitTemplate.setRoutingKey(RabbitMqConstants.AUTO_ACKNOWLEDGE_ROUTING_KEY);
                // 发送消息
                rabbitTemplate.convertAndSend(seckillMessage);
                log.info("确认消费模式为自动确认机制-消息模型DirectExchange-one-生产者-发送消息：{} ", seckillMessage);
            } catch (Exception e) {
                log.error("确认消费模式为自动确认机制-消息模型DirectExchange-one-生产者-发送消息:{},发生异常：{} ",seckillMessage, e);
            }
        }
    }
}
