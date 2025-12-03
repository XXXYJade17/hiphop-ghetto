package com.xxxyjade.hiphopghetto.service.impl;

import com.xxxyjade.hiphopghetto.common.constant.MessageQueue;
import com.xxxyjade.hiphopghetto.common.constant.MessageType;
import com.xxxyjade.hiphopghetto.message.domain.Message;
import com.xxxyjade.hiphopghetto.model.entity.Rating;
import com.xxxyjade.hiphopghetto.server.rating.service.RatingMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RatingMessageServiceImpl implements RatingMessageService {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送评分数递增消息
     */
    public void sendRatingCountIncreaseMessage(Rating rating) {
        Message<Rating> message = Message.<Rating>builder()
                .messageType(MessageType.RATING_COUNT_INCREASE)
                .messageBody(rating)
                .build();
        log.info("发送评分数递增消息：{}", message);
        rabbitTemplate.convertAndSend(
                MessageQueue.RATING_QUEUE,
                message
        );
    }

}
