package com.weiran.mission.rabbitmq.ackmodel.manual;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型-消费者
 */
@Slf4j
@Component
public class ManualAckConsumer {

    @RabbitListener(queues = RabbitMqConstants.MANUAL_ACKNOWLEDGE_QUEUE, containerFactory = "singleListenerContainerManual")
    public void consumeMsg(SeckillMessage SeckillMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("基于MANUAL的手工确认消费模式-消费者监听消费消息,消息投递标记：{},内容为：{} ", tag, SeckillMessage);
            // 抛异常,归入使得消息重新归入队列
            //int num = 1 / 0;
            // 执行完业务逻辑后，手动进行确认消费，其中第一个参数为：消息的分发标识(全局唯一);第二个参数：是否允许批量确认消费
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 第二个参数reueue重新归入队列,true的话会重新归入队列,需要人为地处理此次异常消息,重新归入队列也会继续异常
            channel.basicReject(tag, true);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", SeckillMessage, tag, e);
        }
    }
}
