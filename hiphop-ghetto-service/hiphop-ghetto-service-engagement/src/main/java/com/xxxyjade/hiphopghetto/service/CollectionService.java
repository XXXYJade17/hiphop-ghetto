package com.xxxyjade.hiphopghetto.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.mapper.CollectionMapper;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.entity.Collection;
import com.xxxyjade.hiphopghetto.sender.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CollectionService {

    private final CollectionMapper collectionMapper;
    private final MessageSender messageSender;

    /**
     * 是否收藏
     */
    @Cacheable(
            value = "collection::",
            key = "'isCollected::userId=' + #collection.userId + " +
                  "'&resourceId=' + #collection.resourceId + " +
                  "'&resourceType=' + #collection.resourceType",
            unless = "#result == null"
    )
    public Boolean select(Collection collection) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>(collection);
        collection = collectionMapper.selectOne(wrapper);
        return collection != null && collection.getIsCollected();
    }

    /**
     * 收藏
     */
    @CacheEvict(
            value = "collection::",
            key = "'isCollected::userId=' + #collection.userId + " +
                  "'&resourceId=' + #collection.resourceId + " +
                  "'&resourceType=' + #collection.resourceType"
    )
    public void collect(Collection collection) {
        if(collectionMapper.insertOrUpdate(collection)) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(collection));
        }
    }

    /**
     * 取消收藏
     */
    @CacheEvict(
            value = "collection::",
            key = "'isCollected::userId=' + #collection.userId + " +
                  "'&resourceId=' + #collection.resourceId + " +
                  "'&resourceType=' + #collection.resourceType"
    )
    public void uncollect(Collection collection) {
        int update = collectionMapper.update(collection, null);
        if(update > 0) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(collection));
        }
    }

    private StatsUpdateMessage buildMessage(Collection collection) {
        return StatsUpdateMessage.builder()
                .data(collection)
                .statsType(StatsType.COLLECTION_COUNT)
                .value(collection.getIsCollected() ? 1 : -1)
                .build();
    }

}