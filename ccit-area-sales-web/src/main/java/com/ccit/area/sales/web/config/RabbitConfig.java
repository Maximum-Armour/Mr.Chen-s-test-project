package com.ccit.area.sales.web.config;

import com.ccit.area.sales.common.dingding.constant.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;


    // 配置连接工厂
    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        return factory;
    }

    // 声明一个持久化队列
    @Bean
    public Queue queue() {
        // 持久化队列
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", RabbitmqConstant.EXCHANGE_DEADLETTER_DIRECT) // 绑定死信交换机
                .withArgument("x-dead-letter-routing-key", RabbitmqConstant.ROUTING_DEADLETTER_KEY) // 死信路由键
                .build();
    }

    // 声明正常交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_DIRECT);
    }

    // 正常队列和交换机的绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitmqConstant.ROUTING_KEY);
    }


    //声明死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(RabbitmqConstant.QUEUE_DEADLETTER_QUERE).build();
    }


    //声明死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_DEADLETTER_DIRECT);
    }

    //死信队列和交换机的绑定
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RabbitmqConstant.ROUTING_DEADLETTER_KEY);
    }

    // 配置 RabbitTemplate，添加消息持久化和回调
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        // 配置 ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功投递: {}", correlationData);
            } else {
                log.error("消息投递失败，原因: {}", cause);
                // 进行重试逻辑将消息发送到死信队列
                if (correlationData != null && correlationData instanceof CustomCorrelationData) {
                    CustomCorrelationData customData = (CustomCorrelationData) correlationData;
                    retrySendWithDLQ(rabbitTemplate, RabbitmqConstant.EXCHANGE_DIRECT, RabbitmqConstant.ROUTING_KEY, customData.getMessageContent(), customData);
                }
            }
        });

        // 配置 ReturnCallback（消息未投递到队列）
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("消息被退回: {}，原因: {}", returned.getMessage(), returned.getReplyText());
            // 可以记录日志或者保存到数据库，供后续处理
        });

        return rabbitTemplate;
    }

    // 重试逻辑封装，并将失败的消息发送到死信队列（DLQ）
    private void retrySendWithDLQ(RabbitTemplate rabbitTemplate,
                                  String exchange,
                                  String routingKey,
                                  Object messageContent,
                                  CorrelationData correlationData) {
        // 配置重试策略
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);  // 设置最大重试次数
        retryTemplate.setRetryPolicy(retryPolicy);

        // 配置重试间隔
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(5000);  // 设置重试间隔时间（毫秒），这里是 5 秒
        retryTemplate.setBackOffPolicy(backOffPolicy);

        try {
            // 执行重试逻辑
            retryTemplate.execute(context -> {
                log.info("正在重试发送消息... 尝试次数: {}", context.getRetryCount() + 1);
                rabbitTemplate.convertAndSend(exchange, routingKey, messageContent, correlationData);
                return null;
            }, context -> {
                log.error("重试失败，达到最大尝试次数。将消息发送到死信队列.");
                // 如果重试失败，将消息发送到死信队列
                rabbitTemplate.convertAndSend(
                        RabbitmqConstant.EXCHANGE_DEADLETTER_DIRECT,  // 死信交换机
                        RabbitmqConstant.ROUTING_DEADLETTER_KEY,  // 死信路由键
                        messageContent,  // 原始消息内容
                        correlationData  // 关联数据
                );
                return null;
            });
        } catch (Exception e) {
            log.error("重试过程中发生意外错误: {}", e.getMessage());
        }
    }

}
