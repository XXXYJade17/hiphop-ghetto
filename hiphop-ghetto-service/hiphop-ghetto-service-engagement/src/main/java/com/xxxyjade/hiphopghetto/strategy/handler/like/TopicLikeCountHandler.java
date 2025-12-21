package com.xxxyjade.hiphopghetto.strategy.handler.like;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.service.TopicService;
import com.xxxyjade.hiphopghetto.strategy.LikeCountStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TopicLikeCountHandler implements LikeCountStrategy {

    private final TopicService topicService;

    @Override
    public void handle(StatsUpdateDTO statsUpdateDTO) {
        topicService.updateStats(statsUpdateDTO);
    }

    @Override
    public List<ResourceType> supports() {
        return List.of(ResourceType.TOPIC);
    }

}
