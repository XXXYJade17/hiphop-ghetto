package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 创建Topic交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue statsQueue() {
        return QueueBuilder.durable(RabbitConstant.STATS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RabbitConstant.STATS_DEAD_QUEUE)
                .build();
    }

    @Bean
    public Queue statsDeadQueue() {
        return new Queue(RabbitConstant.STATS_DEAD_QUEUE);
    }

    @Bean
    public Binding bindingStatsQueue(Queue statsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(statsQueue)
                .to(topicExchange)
                .with(RabbitConstant.STATS_ROUTING_PATTERN);
    }

    @Bean
    public Binding bindingStatsDeadQueue(Queue statsDeadQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(statsDeadQueue)
                .to(topicExchange)
                .with(RabbitConstant.STATS_DEAD_ROUTING_KEY);
    }

    /**
     * 创建爬虫队列
     */
    @Bean
    public Queue crawlQueue() {
        return QueueBuilder.durable(RabbitConstant.CRAWl_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RabbitConstant.CRAWl_DEAD_QUEUE)
                .build();
    }

    /**
     * 创建永久死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(RabbitConstant.DEAD_LETTER_QUEUE);
    }

    /**
     * 创建爬虫死信队列
     */
    @Bean
    public Queue crawlDeadQueue() {
        return new Queue(RabbitConstant.CRAWl_DEAD_QUEUE);
    }

    /**
    * 创建消息转换器
    */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }
}
