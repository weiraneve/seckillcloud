package com.weiran.mission.rabbitmq.exchangemodel.direct;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 直连传输directExchange消息模型-消费者
 */
@Slf4j
@Component
public class DirectConsumer {

    /** 
     * 这是第一个路由绑定的对应队列的消费者方法
     * 监听并消费队列中的消息-directExchange-one
     */
    @RabbitListener(queues = RabbitMqConstants.DIRECT_ONE_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeDirectMsgOne(SeckillMessage seckillMessage) {
        try {
            //  打印日志消息
            log.info("消息模型directExchange-one-消费者-监听消费的消息：{} ", seckillMessage);
        } catch (Exception e) {
            log.error("消息模型directExchange-one-消费者-监听消费发生异常：", e);
        }
    }

    /**
     * 这是第二个路由绑定的对应队列的消费者方法
     * 监听并消费队列中的消息-directExchange-two
     */
    @RabbitListener(queues = RabbitMqConstants.DIRECT_TWO_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeDirectMsgTwo(SeckillMessage seckillMessage) {
        try {
            //  打印日志消息
            log.info("消息模型directExchange-two-消费者-监听消费的消息：{} ", seckillMessage);
        } catch (Exception e) {
            log.error("消息模型directExchange-two-消费者-监听消费发生异常：", e);
        }
    }
}
