package com.xxxyjade.hiphopghetto.sender.impl;

import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.sender.IMessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageSender implements IMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public <T> void send(Message<T> message) {
        log.info("发送消息: {}", message);
        rabbitTemplate.convertAndSend(
                message.getQueue(),
                message
        );
    }

}
