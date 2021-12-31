package com.weiran.mission.rabbitmq.exchangemodel.direct;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 直连传输directExchange消息模型-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DirectPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息-基于DirectExchange消息模型-one
     */
    public void sendMsgDirectOne(SeckillMessage seckillMessage) {
        //  判断对象是否为Null
        if (seckillMessage != null) {
            try {
                //  设置交换机
                rabbitTemplate.setExchange(RabbitMqConstants.DIRECT_EXCHANGE);
                //  设置路由1
                rabbitTemplate.setRoutingKey(RabbitMqConstants.DIRECT_ONE_ROUTING_KEY);
                //  发送消息
                rabbitTemplate.convertAndSend(seckillMessage);
                //  打印日志
                log.info("消息模型DirectExchange-one-生产者-发送消息：{} ", seckillMessage);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-one-生产者-发送消息:{},发生异常：{} ", seckillMessage, e);
            }
        }
    }
    /**
     * 发送消息-基于DirectExchange消息模型-two
     */
    public void sendMsgDirectTwo(SeckillMessage seckillMessage) {
        // 判断对象是否为Null
        if (seckillMessage != null) {
            try {
                // 设置交换机
                rabbitTemplate.setExchange(RabbitMqConstants.DIRECT_EXCHANGE);
                // 设置路由2
                rabbitTemplate.setRoutingKey(RabbitMqConstants.DIRECT_TWO_ROUTING_KEY);
                // 发送消息
                rabbitTemplate.convertAndSend(seckillMessage);
                // 打印日志
                log.info("消息模型DirectExchange-two-生产者-发送消息：{} ", seckillMessage);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-two-生产者-发送消息:{},发生异常：{} ", seckillMessage, e);
            }
        }
    }
}
