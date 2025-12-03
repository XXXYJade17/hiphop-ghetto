package com.xxxyjade.hiphopghetto.config;

import com.xxxyjade.hiphopghetto.constant.MessageQueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 创建统计操作队列
     */
    @Bean
    public Queue statsQueue() {
        return QueueBuilder.durable(MessageQueueConstant.STATS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", MessageQueueConstant.STATS_DEAD_QUEUE)
                .build();
    }

    /**
     * 创建永久死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(MessageQueueConstant.DEAD_LETTER_QUEUE);
    }

    /**
     * 创建统计操作死信队列
     */
    @Bean
    public Queue statsDeadQueue() {
        return new Queue(MessageQueueConstant.STATS_DEAD_QUEUE);
    }

    /**
    * 创建消息转换器
    */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }
}
