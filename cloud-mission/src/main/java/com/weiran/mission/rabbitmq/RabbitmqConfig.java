package com.weiran.mission.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    final CachingConnectionFactory connectionFactory;

    // 自动装配消息监听器所在的容器工厂配置类实例
    final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 为单一消费者实例的配置
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 多个消费者实例的配置，主要是针对高并发业务场景的配置
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factoryConfigurer.configure(factory,connectionFactory);
        // 设置消息在传输中的格式。在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置消息的确认消费模式。NONE表示不需要确认消费;AUTO为自动确认;MANUAL为手动确认；自动确认在mq成功消费一条信息后回移除队列所有信息，而手动确认会只移除消费了的信息
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置并发消费者实例的初始数量。在这里为10个
        factory.setConcurrentConsumers(10);
        // 设置并发消费者实例的最大数量。在这里为15个
        factory.setMaxConcurrentConsumers(15);
        // 设置并发消费者实例中每个实例拉取的消息数量。在这里为10个
        factory.setPrefetchCount(10);
        return factory;
    }

    // 自定义配置RabbitMQ发送消息的操作组件RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate() {
        // 设置“发送消息后进行确认”
        connectionFactory.setPublisherConfirms(true);
        // 设置“发送消息后返回确认信息”
        connectionFactory.setPublisherReturns(true);
        // 构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        // 发送消息后，如果发送成功，则输出“消息发送成功”的反馈信息
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        // 发送消息后，如果发送失败，则输出“消息发送失败-消息丢失”的反馈信息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        // 定义消息传输的格式为JSON字符串格式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 最终返回RabbitMQ的操作组件实例RabbitTemplate
        return rabbitTemplate;
    }

    // 创建队列
    @Bean
    public Queue basicQueue() {
        return new Queue(RabbitMqConstants.BASIC_QUEUE,true);
    }

    // 创建交换机：在这里以DirectExchange为例
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(RabbitMqConstants.BASIC_EXCHANGE,true,false);
    }

    // 创建绑定
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(RabbitMqConstants.BASIC_ROUTING_KEY);
    }

    /**
     * 创建消息模型-fanoutExchange
     */
    // 广播fanout消息模型-队列1
    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(RabbitMqConstants.FANOUT_ONE_QUEUE,true);
    }

    // 广播fanout消息模型-队列2
    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(RabbitMqConstants.FANOUT_TWO_QUEUE,true);
    }

    // 广播fanout消息模型-创建交换机-fanoutExchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMqConstants.FANOUT_EXCHANGE,true,false);
    }

    // 广播fanout消息模型-创建绑定1
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    // 广播fanout消息模型-创建绑定2
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    /**
     * 创建消息模型-directExchange
     */
    // 直连传输direct消息模型-创建交换机-directExchange
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitMqConstants.DIRECT_EXCHANGE,true,false);
    }

    // 直连传输direct消息模型-创建队列1
    @Bean
    public Queue directQueueOne() {
        return new Queue(RabbitMqConstants.DIRECT_ONE_QUEUE,true);
    }

    // 直连传输direct消息模型-创建队列2
    @Bean
    public Queue directQueueTwo() {
        return new Queue(RabbitMqConstants.DIRECT_TWO_QUEUE,true);
    }

    // 直连传输direct消息模型-创建绑定1
    @Bean
    public Binding directBindingOne() {
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with(RabbitMqConstants.DIRECT_ONE_ROUTING_KEY);
    }

    // 直连传输direct消息模型-创建绑定2
    @Bean
    public Binding directBindingTwo() {
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with(RabbitMqConstants.DIRECT_TWO_ROUTING_KEY);
    }

    /**
     * 创建消息模型-topicExchange
     */
    // 主题topic消息模型-创建交换机-topicExchange
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMqConstants.TOPIC_EXCHANGE,true,false);
    }

    // 主题topic消息模型-创建队列1
    @Bean
    public Queue topicQueueOne() {
        return new Queue(RabbitMqConstants.TOPIC_ONE_QUEUE,true);
    }

    // 主题topic消息模型-创建队列2
    @Bean
    public Queue topicQueueTwo() {
        return new Queue(RabbitMqConstants.TOPIC_TWO_QUEUE,true);
    }

    // 主题topic消息模型-创建绑定-通配符为*的路由
    @Bean
    public Binding topicBindingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(RabbitMqConstants.TOPIC_ONE_ROUTING_KEY);
    }

    // 主题topic消息模型-创建绑定-通配符为#的路由
    @Bean
    public Binding topicBindingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(RabbitMqConstants.TOPIC_TWO_ROUTING_KEY);
    }

    /**
     * 确认消费模式为自动确认机制-AUTO,采用直连传输directExchange消息模型
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainerAuto() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        // 确认消费模式为自动确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    // 确认消费模式为自动确认机制,采用直连传输directExchange消息模型-交换机
    @Bean
    public DirectExchange autoAckDirectExchange() {
        return new DirectExchange(RabbitMqConstants.AUTO_ACKNOWLEDGE_EXCHANGE, true, false);
    }

    // 确认消费模式为自动确认机制,采用直连传输directExchange消息模型-队列
    @Bean
    public Queue autoAckDirectQueue() {
        return new Queue(RabbitMqConstants.AUTO_ACKNOWLEDGE_QUEUE, true);
    }

    // 确认消费模式为自动确认机制,采用直连传输directExchange消息模型-路由交换机绑定队列
    @Bean
    public Binding autoAckDirectBinding() {
        return BindingBuilder.bind(autoAckDirectQueue()).to(autoAckDirectExchange()).with(RabbitMqConstants.AUTO_ACKNOWLEDGE_ROUTING_KEY);
    }

    /**
     * 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainerManual() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 设置消息在传输中的格式，在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置并发消费者实例的初始数量。在这里为1个
        factory.setConcurrentConsumers(1);
        // 设置并发消费者实例的最大数量。在这里为1个
        factory.setMaxConcurrentConsumers(1);
        // 设置并发消费者实例中每个实例拉取的消息数量-在这里为1个
        factory.setPrefetchCount(1);
        // 确认消费模式为自动确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    // 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型-交换机
    @Bean
    public DirectExchange manualAckDirectExchange() {
        return new DirectExchange(RabbitMqConstants.MANUAL_ACKNOWLEDGE_EXCHANGE, true, false);
    }

    // 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型-队列
    @Bean
    public Queue manualAckDirectQueue() {
        return new Queue(RabbitMqConstants.MANUAL_ACKNOWLEDGE_QUEUE, true);
    }

    // 确认消费模式为手动确认机制-MANUAL,采用直连传输directExchange消息模型-路由交换机绑定队列
    @Bean
    public Binding manualAckDirectBinding() {
        return BindingBuilder.bind(manualAckDirectQueue()).to(manualAckDirectExchange()).with(RabbitMqConstants.MANUAL_ACKNOWLEDGE_ROUTING_KEY);
    }

    // 延迟队列
    @Bean
    public Queue delayQueuePre() {
        // 创建延迟队列的组成成分map，用于存放组成成分的相关成员
        Map<String, Object> args = new <String, Object>HashMap(16);
        // 设置消息过期之后的死信交换机(真正消费的交换机)
        args.put("x-dead-letter-exchange", RabbitMqConstants.DELAY_EXCHANGE);
        // 设置消息过期之后死信队列的路由(真正消费的路由)
        args.put("x-dead-letter-routing-key", RabbitMqConstants.DELAY_ROUTING_KEY);
        // 设定消息的TTL，单位为ms，在这里指的是30s
        args.put("x-message-ttl", 30000);
        return new Queue(RabbitMqConstants.DELAY_QUEUE_PRE, true,false,false, args);
    }

    // 直连传输directExchange消息模型-交换机
    @Bean
    public DirectExchange delayExchangePre() {
        return new DirectExchange(RabbitMqConstants.DELAY_EXCHANGE_PRE, true, false);
    }

    // 直连传输directExchange消息模型-路由交换机绑定队列
    @Bean
    public Binding delayBindingPre() {
        return BindingBuilder.bind(delayQueuePre()).to(delayExchangePre()).with(RabbitMqConstants.DELAY_ROUTING_KEY_PRE);
    }

    // 延迟队列（真正处理消息的队列）
    @Bean
    public Queue delayQueue() {
        return new Queue(RabbitMqConstants.DELAY_QUEUE, true);
    }

    // 死信交换机（真正处理消息的交换机）
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(RabbitMqConstants.DELAY_EXCHANGE, true, false);
    }

    // 死信交换机、死信路由绑定延迟队列
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(RabbitMqConstants.DELAY_ROUTING_KEY);
    }

    // 演示死信队列,为directExchange消息模型队列绑定死信队列
    @Bean
    public Queue directDeadPreQueue() {
        // 创建死信队列的组成成分map，用于存放组成成分的相关成员
        Map<String, Object> args = new <String, Object>HashMap(2);
        // 设死信交换机
        args.put("x-dead-letter-exchange", RabbitMqConstants.DEAD_EXCHANGE);
        // 死信队列的路由
        args.put("x-dead-letter-routing-key", RabbitMqConstants.DEAD_ROUTING_KEY);
        return new Queue(RabbitMqConstants.DIRECT_QUEUE_DEAD_PRE, true, false, false, args);
    }

    // 交换机
    @Bean
    public DirectExchange directDeadPreExchange() {
        return new DirectExchange(RabbitMqConstants.DIRECT_EXCHANGE_DEAD_PRE, true, false);
    }

    // 交换机路由绑定队列
    @Bean
    public Binding directDeadPreBinding() {
        return BindingBuilder.bind(directDeadPreQueue()).to(directDeadPreExchange()).with(RabbitMqConstants.DIRECT_ROUTING_KEY_DEAD_PRE);
    }

    // 死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue(RabbitMqConstants.DEAD_QUEUE, true);
    }

    // 死信交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(RabbitMqConstants.DEAD_EXCHANGE, true, false);
    }

    // 路由交换机绑定死信队列
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(RabbitMqConstants.DEAD_ROUTING_KEY);
    }

    // 优先级队列
    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new <String, Object>HashMap<String, Object>(1);
        // 设置消息优先级,有10个等级,消息不设置优先级默认为0
        args.put("x-max-priority", 10);
        return new Queue(RabbitMqConstants.PRIORITY_QUEUE, true, false, false, args);
    }

    // 优先级队列-交换机
    @Bean
    public DirectExchange priorityExchange() {
        return new DirectExchange(RabbitMqConstants.PRIORITY_EXCHANGE, true,false);
    }

    // 优先级队列-绑定队列
    @Bean
    public Binding priorityBinding() {
        return BindingBuilder.bind(priorityQueue()).to(priorityExchange()).with(RabbitMqConstants.PRIORITY_ROUTING_KEY);
    }

}
