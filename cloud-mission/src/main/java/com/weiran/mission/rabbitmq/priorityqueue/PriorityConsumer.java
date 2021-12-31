package com.weiran.mission.rabbitmq.priorityqueue;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 优先级队列-消费者
 */
@Slf4j
@Component
public class PriorityConsumer {

    @RabbitListener(queues = RabbitMqConstants.PRIORITY_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(SeckillMessage seckillMessage) {
        log.info("优先级队列监听消息：{}", seckillMessage);
    }
}
