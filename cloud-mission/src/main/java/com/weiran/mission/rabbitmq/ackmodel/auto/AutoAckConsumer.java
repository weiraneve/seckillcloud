package com.weiran.mission.rabbitmq.ackmodel.auto;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 确认消费模式为自动确认机制-AUTO,采用直连传输directExchange消息模型-消费者
 */
@Slf4j
@Component
public class AutoAckConsumer {

    @RabbitListener(queues = RabbitMqConstants.AUTO_ACKNOWLEDGE_QUEUE, containerFactory = "singleListenerContainerAuto")
    public void consumeMsg(SeckillMessage seckillMessage) {
        try {
            log.info("基于AUTO的自动确认消费模式-消费者监听消费消息-内容为：{} ", seckillMessage);
        } catch (Exception e) {
            log.error("基于AUTO的自动确认消费模式-消费者监听消费消息:{},发生异常：", seckillMessage, e);
        }
    }
}
