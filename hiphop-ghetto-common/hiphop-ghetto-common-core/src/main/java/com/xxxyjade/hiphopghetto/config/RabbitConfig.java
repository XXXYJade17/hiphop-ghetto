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
     * 创建爬虫队列
     */
    @Bean
    public Queue crawlQueue() {
        return QueueBuilder.durable(MessageQueueConstant.CRAWl_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", MessageQueueConstant.CRAWl_DEAD_QUEUE)
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
     * 创建爬虫死信队列
     */
    @Bean
    public Queue crawlDeadQueue() {
        return new Queue(MessageQueueConstant.CRAWl_DEAD_QUEUE);
    }

    /**
    * 创建消息转换器
    */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }
}
