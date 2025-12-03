package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.service.ISongService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SongCollectionCountDecreaseHandler implements IStrategy<StatsOperationEvent>{

    private final ISongService songService;

    @Override
    public void handle(StatsOperationEvent event) {
        songService.decreaseCollectionCount(event.getId());
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.SONG;
    }

    @Override
    public StatsType getStatsType() {
        return StatsType.COLLECTION_COUNT;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.COUNT_DECREASE;
    }

}
