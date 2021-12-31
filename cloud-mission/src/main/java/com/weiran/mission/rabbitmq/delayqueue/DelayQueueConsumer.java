package com.weiran.mission.rabbitmq.delayqueue;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 延迟队列-消费者
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    @RabbitListener(queues = RabbitMqConstants.DELAY_QUEUE, containerFactory = "singleListenerContainerAuto")
    public void consumeMsg(SeckillMessage seckillMessage) {
        try {
            log.info("延迟队列-30s时间到达后,真正消费消息的队列,监听消息：{},当前时间：{}", seckillMessage, LocalDateTime.now());
        } catch (Exception e) {
            log.error("延迟队列-30s时间到达后,真正消费消息的队列,监听消息：{},处理发生异常e：", seckillMessage, e);
        }
    }
}
