package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.service.ICommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CommentLikeCountDecreaseHandler implements IStrategy<StatsOperationEvent>{

    private final ICommentService commentService;

    @Override
    public void handle(StatsOperationEvent event) {
        commentService.decreaseLikeCount(event.getId());
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.COMMENT;
    }

    @Override
    public StatsType getStatsType() {
        return StatsType.LIKE_COUNT;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.COUNT_DECREASE;
    }

}
