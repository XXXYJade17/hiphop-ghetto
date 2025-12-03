package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.IEvent;

public interface IStrategy<T extends IEvent> {

    void handle(T event);

    ResourceType getResourceType();

    StatsType getStatsType();

    OperationType getOperationType();

}
