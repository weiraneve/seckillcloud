package com.weiran.mission.rabbitmq.exchangemodel.fanout;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 广播fanoutExchange消息模型-生产者
 * @author zhengdayue
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FanoutPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void sendMsg(SeckillMessage seckillMessage) {
        //判断是否为Null
        if (seckillMessage != null) {
            try {
                // 设置广播式交换机FanoutExchange
                rabbitTemplate.setExchange(RabbitMqConstants.FANOUT_EXCHANGE);
                // 发送消息
                rabbitTemplate.convertAndSend(seckillMessage);
                // 打印日志
                log.info("消息模型fanoutExchange-生产者-发送消息：{} ", seckillMessage);
            } catch (Exception e) {
                log.error("消息模型fanoutExchange-生产者-发送消息:{},发生异常： ", seckillMessage, e);
            }
        }
    }
}
