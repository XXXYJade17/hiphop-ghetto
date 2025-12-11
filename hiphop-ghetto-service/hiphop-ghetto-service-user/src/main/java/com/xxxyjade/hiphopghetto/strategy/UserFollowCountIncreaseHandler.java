package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserFollowCountIncreaseHandler implements IStrategy<StatsOperationEvent>{

    private final IUserService userService;

    @Override
    public void handle(StatsOperationEvent event) {
        userService.increaseFollowCount(event.getResourceId());
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.USER;
    }

    @Override
    public StatsType getStatsType() {
        return StatsType.FOLLOW_COUNT;
    }

    @Override
    public Operation getOperationType() {
        return Operation.COUNT_INCREASE;
    }

}
