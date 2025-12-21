package com.xxxyjade.hiphopghetto.strategy.handler.like;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.service.CommentService;
import com.xxxyjade.hiphopghetto.strategy.LikeCountStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentLikeCountHandler implements LikeCountStrategy {

    private final CommentService commentService;

    @Override
    public void handle(StatsUpdateDTO statsUpdateDTO) {
        commentService.updateStats(statsUpdateDTO);
    }

    @Override
    public List<ResourceType> supports() {
        return List.of(ResourceType.COMMENT);
    }

}
