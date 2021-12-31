package com.weiran.mission.rabbitmq.deadqueue;

import com.rabbitmq.client.Channel;
import com.weiran.mission.rabbitmq.SeckillMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeadQueueConsumer {

    private final OrdinaryPublisher ordinaryPublisher;

    // 为方便演示,写死在这里,实际可以用配置中心apollo或者阿里云nacos动态刷新该值,即修复bug之后刷新该值为true
    private Boolean dynamicRepairSign = false;

    // 可以注释掉监听,在rabbitmq管理后台取出该消息,等到异常处理完之后把该消息丢回原先的队列进行处理。
    //@RabbitListener(queues = RabbitMqConstants.DEAD_QUEUE, containerFactory = "singleListenerContainerManual")
    public void consumeMsg(SeckillMessage seckillMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        log.info("死信队列监听到消息：{}", seckillMessage);
        if (dynamicRepairSign) {
            //修复完异常之后发送消息到原先队列进行消费
            ordinaryPublisher.sendMsg(seckillMessage);
            channel.basicAck(tag, false);
        } else {
            channel.basicReject(tag, true);
        }
    }
}
