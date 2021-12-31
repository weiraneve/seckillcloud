package com.weiran.mission.rabbitmq;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * rabbitmq demo-生产者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BasicPublisher {

    // 定义RabbitMQ消息操作组件RabbitTemplate
    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param seckillMessage 待发送的消息
     */
    public void sendMsg(SeckillMessage seckillMessage) {
        if (seckillMessage != null) {
            try {
                // 指定消息模型中的交换机
                rabbitTemplate.setExchange(RabbitMqConstants.BASIC_EXCHANGE);
                // 指定消息模型中的路由
                rabbitTemplate.setRoutingKey(RabbitMqConstants.BASIC_ROUTING_KEY);
                // 转化并发送消息
                rabbitTemplate.convertAndSend(seckillMessage);
                // 打印日志信息
                log.info("rabbitmq demo-生产者-发送消息：{} ", JSONUtil.toJsonStr(seckillMessage));
            } catch (Exception e) {
                log.error("rabbitmq demo-生产者-发送消息发生异常：{} ", seckillMessage, e.fillInStackTrace());
            }
        }
    }

}
