package com.xxxyjade.hiphopghetto.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.mapper.LikeMapper;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Like;
import com.xxxyjade.hiphopghetto.sender.MessageSender;
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
public class LikeService {

    private final LikeMapper likeMapper;
    private final MessageSender messageSender;
    private final RedisUtil redisUtil;

    /**
     * 是否点赞
     */
    @Cacheable(
            value = "like::",
            key = "'isLiked::userId=' + #like.userId + " +
                  "'&resourceId=' + #like.resourceId + " +
                  "'&resourceType=' + #like.resourceType",
            unless = "#result == null"
    )
    public Boolean select(Like like) {
        QueryWrapper<Like> wrapper = new QueryWrapper<>(like);
        like = likeMapper.selectOne(wrapper);
        return like != null && like.getIsLiked();
    }

    /**
     * 点赞
     */
    @CacheEvict(
            value = "like",
            key = "'isLiked::userId=' + #like.userId + '" +
                  "&resourceId=' + #like.resourceId + '" +
                  "&resourceType=' + #like.resourceType"
    )
    public void like(Like like) {
        if(likeMapper.insertOrUpdate(like)) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(like));
        }
    }

    /**
     * 取消点赞
     */
    @CacheEvict(
            value = "like",
            key = "'isLiked::userId=' + #like.userId + '" +
                  "&resourceId=' + #like.resourceId + '" +
                  "&resourceType=' + #like.resourceType"
    )
    public void unlike(Like like) {
        int update = likeMapper.update(like, null);
        if(update > 0) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(like));
        }
    }

    /**
     * 批量查询点赞
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
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<Like>()
                    .eq(Like::getUserId, userId)
                    .in(Like::getResourceId, hasNotCachedIds);
            List<Like> likes = likeMapper.selectList(wrapper);
            // 缓存点赞状态
            likes.forEach(like -> {
                isLikedMap.put(like.getResourceId(), like.getIsLiked());
                redisUtil.set(cacheKeyPrefix + like.getResourceId(), like.getIsLiked());
            });
        }
        return isLikedMap;
    }

    private StatsUpdateMessage buildMessage(Like like) {
        StatsUpdateDTO statsUpdateDTO = StatsUpdateDTO.builder()
                .id(like.getResourceId())
                .resourceType(like.getResourceType())
                .statsType(StatsType.LIKE_COUNT)
                .value(like.getIsLiked() ? 1 : -1)
                .build();
        return StatsUpdateMessage.builder()
                .support(Strategy.LIKE_STATS)
                .statsUpdateDTO(statsUpdateDTO)
                .build();
    }

}