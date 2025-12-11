package com.xxxyjade.hiphopghetto.sender;

import com.xxxyjade.hiphopghetto.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(String queue, Message message) {
        log.info("发送消息: {}", message);
        rabbitTemplate.convertAndSend(queue, message);
    }

}
