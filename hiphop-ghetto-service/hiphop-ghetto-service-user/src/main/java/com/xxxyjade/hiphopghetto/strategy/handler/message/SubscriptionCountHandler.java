package com.xxxyjade.hiphopghetto.strategy.handler.message;

import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Subscription;
import com.xxxyjade.hiphopghetto.service.UserService;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SubscriptionCountHandler implements MessageHandler<StatsUpdateMessage> {

    private final UserService userService;

    @Override
    public void handle(StatsUpdateMessage message) {
        if (message.getData() instanceof Subscription subscription) {
            StatsUpdateDTO subscriptionCountUpdateDTO = StatsUpdateDTO.builder()
                    .id(subscription.getUserId())
                    .statsType(StatsType.SUBSCRIPTION_COUNT)
                    .value(message.getValue())
                    .build();
            StatsUpdateDTO fanCountUpdateDTO = StatsUpdateDTO.builder()
                    .id(subscription.getUserId())
                    .statsType(StatsType.FAN_COUNT)
                    .value(message.getValue())
                    .build();
            userService.updateStats(subscriptionCountUpdateDTO);
            userService.updateStats(fanCountUpdateDTO);
        }

    }

    @Override
    public List<Strategy> supports() {
        return List.of(Strategy.SUBSCRIPTION_COUNT);
    }
}
