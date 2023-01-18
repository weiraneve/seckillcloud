package com.weiran.mission.rabbitmq;

import lombok.Data;

/**
 * RabbitMQ创建队列、交换机以及路由规则的常量类
 */
@Data
public class RabbitMqConstants {

    public static final String BASIC_QUEUE = "mq.basic.info.queue";

    public static final String BASIC_EXCHANGE = "mq.basic.info.exchange";

    public static final String BASIC_ROUTING_KEY = "mq.basic.info.routing.key";

    // 广播fanoutExchange消息模型
    public static final String FANOUT_ONE_QUEUE = "mq.fanout.one.queue";

    public static final String FANOUT_TWO_QUEUE = "mq.fanout.two.queue";

    public static final String FANOUT_EXCHANGE = "mq.fanout.exchange";

    // 直连directExchange消息模型
    public static final String DIRECT_ONE_QUEUE = "mq.direct.one.queue";

    public static final String DIRECT_TWO_QUEUE = "mq.direct.two.queue";

    public static final String DIRECT_ONE_ROUTING_KEY = "mq.direct.routing.key.one";

    public static final String DIRECT_TWO_ROUTING_KEY = "mq.direct.routing.key.two";

    public static final String DIRECT_EXCHANGE = "mq.direct.exchange";

    // 主题topicExchange消息模型
    public static final String TOPIC_ONE_QUEUE = "mq.topic.one.queue";

    public static final String TOPIC_TWO_QUEUE = "mq.topic.two.queue";

    public static final String TOPIC_ONE_ROUTING_KEY = "mq.topic.routing.key.*";

    public static final String TOPIC_TWO_ROUTING_KEY = "mq.topic.routing.key.#";

    public static final String TOPIC_EXCHANGE = "mq.topic.exchange";

    // 确认消费模式为自动确认机制,采用直连传输directExchange消息模型
    public static final String AUTO_ACKNOWLEDGE_QUEUE = "mq.auto.acknowledge.direct.queue";

    public static final String AUTO_ACKNOWLEDGE_ROUTING_KEY = "mq.auto.acknowledge.direct.routing.key";

    public static final String AUTO_ACKNOWLEDGE_EXCHANGE = "mq.auto.acknowledge.direct.exchange";

    // 确认消费模式为手动确认机制,采用直连传输directExchange消息模型
    public static final String MANUAL_ACKNOWLEDGE_QUEUE = "mq.manual.acknowledge.direct.queue";

    public static final String MANUAL_ACKNOWLEDGE_ROUTING_KEY = "mq.manual.acknowledge.direct.routing.key";

    public static final String MANUAL_ACKNOWLEDGE_EXCHANGE = "mq.manual.acknowledge.direct.exchange";

    // 演示延迟队列,为directExchange消息模型队列绑定延迟队列
    public static final String DELAY_QUEUE_PRE = "mq.direct.queue.delay.pre";

    public static final String DELAY_EXCHANGE_PRE = "mq.direct.exchange.delay.pre";

    public static final String DELAY_ROUTING_KEY_PRE = "mq.routing.key.delay.pre";

    // 延迟队列
    public static final String DELAY_QUEUE = "mq.delay.queue";

    public static final String DELAY_EXCHANGE = "mq.delay.exchange";

    public static final String DELAY_ROUTING_KEY = "mq.delay.routing.key";

    // 演示死信队列,为directExchange消息模型队列绑定死信队列
    public static final String DIRECT_QUEUE_DEAD_PRE = "mq.direct.queue.dead.pre";

    public static final String DIRECT_EXCHANGE_DEAD_PRE = "mq.direct.exchange.dead.pre";

    public static final String DIRECT_ROUTING_KEY_DEAD_PRE = "mq.direct.routing.key.dead.pre";

    // 死信队列
    public static final String DEAD_QUEUE = "mq.dead.queue";

    public static final String DEAD_EXCHANGE = "mq.dead.exchange";

    public static final String DEAD_ROUTING_KEY = "mq.dead.routing.key";

    // 优先级队列
    public static final String PRIORITY_QUEUE = "mq.priority.direct.queue";

    public static final String PRIORITY_EXCHANGE = "mq.priority.direct.exchange";

    public static final String PRIORITY_ROUTING_KEY = "mq.priority.direct.routing.key";

}
