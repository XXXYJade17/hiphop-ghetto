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
public class LikeService implements ILikeService {

    private final LikeMapper likeMapper;
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
        // 查询点赞状态
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
        // 创建点赞实体
        Like like = Like.builder()
                .userId(likeDTO.getUserId())
                .resourceId(likeDTO.getResourceId())
                .resourceType(likeDTO.getResourceType())
                .isLiked(true)
                .build();

        // 插入或更新
        likeMapper.upsert(like);

        // 发布事件
        StatsOperationEvent event = StatsOperationEvent.builder()
                .resourceId(like.getResourceId())
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
        // 创建点赞实体
        Like like = Like.builder()
                .userId(likeDTO.getUserId())
                .resourceId(likeDTO.getResourceId())
                .resourceType(likeDTO.getResourceType())
                .isLiked(false)
                .build();

        // 插入或更新
        likeMapper.upsert(like);

        // 发布事件
        StatsOperationEvent event = StatsOperationEvent.builder()
                .resourceId(like.getResourceId())
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
        // 存储未缓存id
        List<Long> hasNotCachedIds = new ArrayList<>();
        // 存储点赞状态
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        // 缓存key前缀
        String cacheKeyPrefix = "like::userId=" + userId + "&targetId=";

        // 遍历查询缓存
        ids.forEach(id -> {
            // 从缓存中查询
            Boolean isLiked = (Boolean) redisUtil.get(cacheKeyPrefix + id);
            if (isLiked != null) {
                // 缓存命中
                isLikedMap.put(id, isLiked);
            } else {
                // 储存未缓存id
                hasNotCachedIds.add(id);
            }
        });

        // 对为缓存id进行缓存
        if (!hasNotCachedIds.isEmpty()) {
            // 批量查询点赞状态
            QueryWrapper<Like> wrapper = new QueryWrapper<Like>()
                    .eq("user_id", userId)
                    .in("target_id", hasNotCachedIds);
            List<Like> likes = likeMapper.selectList(wrapper);
            // 缓存点赞状态
            likes.forEach(like -> {
                isLikedMap.put(like.getResourceId(), like.getIsLiked());
                redisUtil.set(cacheKeyPrefix + like.getResourceId(), like.getIsLiked());
            });
        }
        return isLikedMap;
    }

}