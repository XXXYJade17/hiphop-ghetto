package com.xxxyjade.hiphopghetto.service.impl;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.mapper.UserFollowMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.UserFollowDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Follow;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.IUserFollowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserFollowService implements IUserFollowService {

    private final UserFollowMapper userFollowMapper;
    private final EventPublisher eventPublisher;

    public void follow(UserFollowDTO userFollowDTO) {
        Follow follow = Follow.builder()
                .userId(userFollowDTO.getUserId())
                .followId(userFollowDTO.getFollowId())
                .isFollowed(userFollowDTO.getIsFollow())
                .build();
        int upsert = userFollowMapper.upsert(follow);
        if (upsert > 0) {
            // 发布事件
            StatsOperationEvent followIncreaseEvent = StatsOperationEvent.builder()
                    .resourceId(follow.getUserId())
                    .resource(ResourceType.USER)
                    .statsType(StatsType.FOLLOW_COUNT)
                    .operation(Operation.COUNT_INCREASE)
                    .build();
            StatsOperationEvent fansIncreaseEvent = StatsOperationEvent.builder()
                    .resourceId(follow.getFollowId())
                    .resource(ResourceType.USER)
                    .statsType(StatsType.FANS_COUNT)
                    .operation(Operation.COUNT_INCREASE)
                    .build();
            eventPublisher.publish(followIncreaseEvent);
            eventPublisher.publish(fansIncreaseEvent);
        }
    }

    public void unfollow(UserFollowDTO userFollowDTO) {
        Follow follow = Follow.builder()
                .userId(userFollowDTO.getUserId())
                .followId(userFollowDTO.getFollowId())
                .isFollowed(userFollowDTO.getIsFollow())
                .build();

        int upsert = userFollowMapper.upsert(follow);

        if (upsert > 0) {
            // 发布事件
            StatsOperationEvent followDecreaseEvent = StatsOperationEvent.builder()
                    .resourceId(follow.getUserId())
                    .resource(ResourceType.USER)
                    .statsType(StatsType.FOLLOW_COUNT)
                    .operation(Operation.COUNT_DECREASE)
                    .build();
            StatsOperationEvent fansDecreaseEvent = StatsOperationEvent.builder()
                    .resourceId(follow.getFollowId())
                    .resource(ResourceType.USER)
                    .statsType(StatsType.FANS_COUNT)
                    .operation(Operation.COUNT_DECREASE)
                    .build();
            eventPublisher.publish(followDecreaseEvent);
            eventPublisher.publish(fansDecreaseEvent);
        }
    }

}
