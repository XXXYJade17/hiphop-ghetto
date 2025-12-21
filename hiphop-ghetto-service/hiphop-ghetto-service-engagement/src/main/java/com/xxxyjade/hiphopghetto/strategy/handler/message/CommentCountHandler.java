package com.xxxyjade.hiphopghetto.strategy.handler.message;

import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import com.xxxyjade.hiphopghetto.strategy.dispatcher.CommentCountHandlerDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class CommentCountHandler implements MessageHandler<StatsUpdateMessage> {

    private final CommentCountHandlerDispatcher dispatcher;

    @Override
    public void handle(StatsUpdateMessage message) {
        if (message.getData() instanceof Comment comment) {
            StatsUpdateDTO statsUpdateDTO = StatsUpdateDTO.builder()
                    .id(comment.getParentId())
                    .resourceType(comment.getParentType())
                    .statsType(StatsType.COMMENT_COUNT)
                    .value(message.getValue())
                    .build();
            dispatcher.dispatch(statsUpdateDTO);

            if (!Objects.equals(comment.getRootId(), comment.getParentId())
                    && comment.getRootType().equals(comment.getParentType())) {
                statsUpdateDTO.setId(comment.getRootId());
                statsUpdateDTO.setResourceType(comment.getRootType());
                dispatcher.dispatch(statsUpdateDTO);
            }
        }
    }

    @Override
    public List<Strategy> supports() {
        return List.of(Strategy.COMMENT_COUNT);
    }
}
