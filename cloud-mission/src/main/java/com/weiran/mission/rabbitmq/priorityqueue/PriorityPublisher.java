package com.weiran.mission.rabbitmq.priorityqueue;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 优先级队列-生产者
 * @author zhengdayue
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PriorityPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param seckillMessage 订单消息
     * @param priority 优先级0-10
     */
    public void sendMsg(SeckillMessage seckillMessage, Integer priority) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.PRIORITY_EXCHANGE, RabbitMqConstants.PRIORITY_ROUTING_KEY, seckillMessage, message -> {
            message.getMessageProperties().setPriority(priority);
            return message;
        });
        log.info("优先级队列发送消息：{},优先级为：{}", seckillMessage, priority);
    }

}
