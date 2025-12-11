package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.service.ITopicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TopicLikeCountIncreaseHandler implements IStrategy<StatsOperationEvent>{

    private final ITopicService topicService;

    @Override
    public void handle(StatsOperationEvent event) {
        topicService.decreaseLikeCount(event.getResourceId());
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.TOPIC;
    }

    @Override
    public StatsType getStatsType() {
        return StatsType.LIKE_COUNT;
    }

    @Override
    public Operation getOperationType() {
        return Operation.COUNT_INCREASE;
    }

}
