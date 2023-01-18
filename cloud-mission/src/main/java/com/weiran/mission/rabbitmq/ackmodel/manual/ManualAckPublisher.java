package com.weiran.mission.rabbitmq.ackmodel.manual;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ManualAckPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendMsg(SeckillMessage seckillMessage) {
        if (seckillMessage != null) {
            try {
                rabbitTemplate.setExchange(RabbitMqConstants.MANUAL_ACKNOWLEDGE_EXCHANGE);
                rabbitTemplate.setRoutingKey(RabbitMqConstants.MANUAL_ACKNOWLEDGE_ROUTING_KEY);
                rabbitTemplate.convertAndSend(seckillMessage);
                log.info("确认消费模式为手动确认机制-消息模型-生产者-发送消息：{} ", seckillMessage);
            } catch (Exception e) {
                log.error("确认消费模式为手动确认机制-消息模型DirectExchange-one-生产者-发送消息:{},发生异常：{} ", seckillMessage, e);
            }
        }
    }
}
