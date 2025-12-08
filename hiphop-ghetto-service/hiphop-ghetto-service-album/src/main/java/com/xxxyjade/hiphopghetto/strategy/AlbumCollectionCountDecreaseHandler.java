package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.service.IAlbumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AlbumCollectionCountDecreaseHandler implements IStrategy<StatsOperationEvent>{

    private final IAlbumService albumService;

    @Override
    public void handle(StatsOperationEvent event) {
        albumService.decreaseCollectionCount(event.getResourceId().toString());
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.ALBUM;
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
