package com.weiran.mission.rabbitmq.deadqueue;


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
 * 普通队列-消费者
 * @author zhengdayue
 */
@Slf4j
@Component
public class OrdinaryConsumer {

    @RabbitListener(queues = RabbitMqConstants.DIRECT_QUEUE_DEAD_PRE, containerFactory = "singleListenerContainerManual")
    public void consumeMsg(SeckillMessage SeckillMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("普通队列-消费者,监听到消息：{},准备处理业务逻辑。", SeckillMessage);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("普通队列-消费者,监听到消息：{},发生异常,消息不再归入队列中,转向死信队列,异常e：", SeckillMessage, e);
            //channel.basicNack(tag, false, false);
            channel.basicReject(tag, false);
        }
    }
}
