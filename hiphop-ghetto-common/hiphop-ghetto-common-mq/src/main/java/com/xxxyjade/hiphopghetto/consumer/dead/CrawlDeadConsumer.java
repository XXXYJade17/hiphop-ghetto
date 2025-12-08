package com.xxxyjade.hiphopghetto.consumer.dead;

import com.xxxyjade.hiphopghetto.constant.MessageQueueConstant;
import com.xxxyjade.hiphopghetto.dispatcher.MessageHandlerDispatcher;
import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import com.xxxyjade.hiphopghetto.sender.impl.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@AllArgsConstructor
public class CrawlDeadConsumer {

    private final MessageSender messageSender;
    // 记录消息重试次数
    private final Map<String, AtomicInteger> retryCountMap = new ConcurrentHashMap<>();

    // 监听收藏死信队列
    @RabbitListener(queues = MessageQueueConstant.CRAWl_DEAD_QUEUE)
    public void handleDeadLetterMessage(Message<String> message) {
        String id = message.getId();
        AtomicInteger retryCount = retryCountMap.computeIfAbsent(
                id,
                k -> new AtomicInteger(0)
        );

        log.info("接受到死信消息: {}, 当前尝试次数: {}", message, retryCount.get());

        // 如果重试次数小于3次，重新发送到原队列
        if (retryCount.incrementAndGet() <= 3) {
            log.info("当前尝试消息Id: {}", id);
            // 延迟3秒后重试
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            messageSender.send(message);
        } else {
            log.error("超过重试次数，发送到永久失败队列: {}", message);
            message.setQueue(MessageQueueConstant.DEAD_LETTER_QUEUE);
            retryCountMap.remove(id);
        }
    }

}
