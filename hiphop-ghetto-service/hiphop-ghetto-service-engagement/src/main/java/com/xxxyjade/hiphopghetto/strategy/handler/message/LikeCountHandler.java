package com.xxxyjade.hiphopghetto.strategy.handler.message;

import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Like;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import com.xxxyjade.hiphopghetto.strategy.dispatcher.LikeCountHandlerDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LikeCountHandler implements MessageHandler<StatsUpdateMessage> {

    private final LikeCountHandlerDispatcher dispatcher;

    @Override
    public void handle(StatsUpdateMessage message) {
        if (message.getData() instanceof Like like) {
            StatsUpdateDTO statsUpdateDTO = StatsUpdateDTO.builder()
                    .id(like.getResourceId())
                    .resourceType(like.getResourceType())
                    .statsType(StatsType.LIKE_COUNT)
                    .value(message.getValue())
                    .build();
            dispatcher.dispatch(statsUpdateDTO);
        }
    }

    @Override
    public List<Strategy> supports() {
        return List.of(Strategy.LIKE_COUNT);
    }
}
