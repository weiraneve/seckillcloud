package com.weiran.mission.rabbitmq.exchangemodel.topic;

import com.weiran.mission.rabbitmq.SeckillMessage;
import com.weiran.mission.rabbitmq.RabbitMqConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 主题topicExchange消息模型-消费者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TopicConsumer {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 监听并消费队列中的消息-topicExchange-*通配符
     */
    @RabbitListener(queues = RabbitMqConstants.TOPIC_ONE_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeTopicMsgOne(SeckillMessage seckillMessage) {
        try {
            log.info("消息模型topicExchange-*-消费者-监听消费的消息：{} ", seckillMessage);
        } catch (Exception e) {
            log.error("消息模型topicExchange-*-消费者-监听消费发生异常：", e);
        }
    }

    /**
     * 监听并消费队列中的消息-topicExchange-#通配符
     */
    @RabbitListener(queues = RabbitMqConstants.TOPIC_TWO_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeTopicMsgTwo(SeckillMessage seckillMessage) {
        try {
            log.info("消息模型topicExchange-#-消费者-监听消费的消息：{} ", seckillMessage);
        } catch (Exception e) {
            log.error("消息模型topicExchange-#-消费者-监听消费发生异常：", e);
        }
    }
}
