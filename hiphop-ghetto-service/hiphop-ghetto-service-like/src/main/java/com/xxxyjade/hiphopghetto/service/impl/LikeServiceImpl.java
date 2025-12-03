package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.mapper.LikeMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.LikeDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Like;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.ILikeService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class LikeServiceImpl implements ILikeService {

    private final LikeMapper likeMapper;
//    private final LikeMessageService likeMessageService;
//    private final CommentMapper commentMapper;
//    private final TopicMapper topicMapper;
    private final EventPublisher eventPublisher;
    private final RedisUtil redisUtil;

    /**
     * 查询点赞状态
     */
    @Cacheable(
            value = "like::",
            key = "'isLiked::userId=' + #likeDTO.userId + " +
                    "'&resourceId=' + #likeDTO.resourceId + " +
                    "'&resourceType=' + #likeDTO.resourceType",
            unless = "#result == null"
    )
    public Boolean isLiked(LikeDTO likeDTO) {
        Like like = Like.builder()
                .userId(likeDTO.getUserId())
                .resourceId(likeDTO.getResourceId())
                .resourceType(likeDTO.getResourceType())
                .build();
        like = likeMapper.selectOne(new QueryWrapper<>(like));
        return like != null && like.getIsLiked();
    }

    /**
     * 创建点赞
     */
    @CacheEvict(
            value = "like",
            key = "'isLiked::userId=' + #likeDTO.userId + '" +
                    "&resourceId=' + #likeDTO.resourceId + '" +
                    "&resourceType=' + #likeDTO.resourceType"
    )
    public void like(LikeDTO likeDTO) {
        Like like = Like.builder()
                .userId(likeDTO.getUserId())
                .resourceId(likeDTO.getResourceId())
                .resourceType(likeDTO.getResourceType())
                .isLiked(true)
                .build();

        likeMapper.upsert(like);

        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(like.getId())
                .resourceType(likeDTO.getResourceType())
                .statsType(StatsType.LIKE_COUNT)
                .operationType(OperationType.COUNT_INCREASE)
                .build();

        eventPublisher.publish(event);
    }

    /**
     * 取消收藏
     */
    @CacheEvict(
            value = "like",
            key = "'isLiked::userId=' + #likeDTO.userId + '" +
                    "&resourceId=' + #likeDTO.resourceId + '" +
                    "&resourceType=' + #likeDTO.resourceType"
    )
    public void cancel(LikeDTO likeDTO) {
        Like like = Like.builder()
                .userId(likeDTO.getUserId())
                .resourceId(likeDTO.getResourceId())
                .resourceType(likeDTO.getResourceType())
                .isLiked(false)
                .build();

        likeMapper.upsert(like);

        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(like.getId())
                .resourceType(likeDTO.getResourceType())
                .statsType(StatsType.LIKE_COUNT)
                .operationType(OperationType.COUNT_DECREASE)
                .build();

        eventPublisher.publish(event);
    }

    /**
     * 批量查询点赞状态
     */
    public Map<Long, Boolean> selectBatch(Long userId, List<Long> ids) {
        List<Long> hasNotCachedIds = new ArrayList<>();
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        String cacheKeyPrefix = "like::userId=" + userId + "&targetId=";
        ids.forEach(id -> {
            Boolean isLiked = (Boolean) redisUtil.get(cacheKeyPrefix + id);
            if (isLiked != null) {
                isLikedMap.put(id, isLiked);
            } else {
                hasNotCachedIds.add(id);
            }
        });
        if (!hasNotCachedIds.isEmpty()) {
            QueryWrapper<Like> wrapper = new QueryWrapper<Like>()
                    .eq("user_id", userId)
                    .in("target_id", hasNotCachedIds);
            List<Like> likes = likeMapper.selectList(wrapper);
            likes.forEach(like -> {
                isLikedMap.put(like.getResourceId(), like.getIsLiked());
                redisUtil.set(cacheKeyPrefix + like.getResourceId(), like.getIsLiked());
            });
        }
        return isLikedMap;
    }

}