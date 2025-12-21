package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.mapper.UserSubscriptionMapper;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.entity.Subscription;
import com.xxxyjade.hiphopghetto.sender.MessageSender;
import com.xxxyjade.hiphopghetto.websocket.handler.NotificationWebSocketHandler;
import com.xxxyjade.hiphopghetto.websocket.message.NotificationMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionMapper userSubscriptionMapper;
    private final MessageSender messageSender;

    public void subscribe(Subscription subscription) {
        if (userSubscriptionMapper.insertOrUpdate(subscription)) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(subscription));

            NotificationMessage message = NotificationMessage.builder()
                    .userId(subscription.getUserId())
                    .content("You have been subscribed by user " + subscription.getUserId())
                    .createTime(LocalDateTime.now())
                    .build();
            NotificationWebSocketHandler.sendNotificationToUser(
                    subscription.getSubscribedId(),
                    message
            );
        }
    }

    public void unsubscribe(Subscription subscription) {
        int update = userSubscriptionMapper.update(subscription, null);

        if (update > 0) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(subscription));
        }
    }

    private StatsUpdateMessage buildMessage(Subscription subscription) {
        return StatsUpdateMessage.builder()
                .data(subscription)
                .statsType(StatsType.SUBSCRIPTION_COUNT)
                .value(subscription.getIsSubscribed() ? 1 : -1)
                .build();
    }

}
