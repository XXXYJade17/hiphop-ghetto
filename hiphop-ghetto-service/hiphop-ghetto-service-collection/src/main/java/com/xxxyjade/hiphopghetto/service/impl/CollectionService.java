package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.mapper.CollectionMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.CollectionDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.IsCollectedDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Collection;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.ICollectionService;
import com.xxxyjade.hiphopghetto.util.ThreadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class CollectionService implements ICollectionService {

    private final CollectionMapper collectionMapper;
    private final EventPublisher eventPublisher;

    /**
     * 查询收藏状态
     */
    @Cacheable(
            value = "collection::",
            key = "'isCollected::userId=' + #isCollectedDTO.userId + '&resourceId=' + #isCollectedDTO.resourceId + '&resourceType=' + #isCollectedDTO.resourceType",
            unless = "#result == null"
    )
    public Boolean select(IsCollectedDTO isCollectedDTO) {
        Collection collection = Collection.builder()
                .userId(ThreadUtil.getUserId())
                .resourceId(isCollectedDTO.getResourceId())
                .resourceType(isCollectedDTO.getResourceType())
                .build();
        collection = collectionMapper.selectOne(new QueryWrapper<>(collection));
        return collection != null && collection.getIsCollected();
    }

    /**
     * 创建收藏
     */
    @CacheEvict(
            value = "collection::",
            key = "'isCollected::userId=' + #collectionDTO.userId + '&resourceId=' + #collectionDTO.resourceId + '&resourceType=' + #collectionDTO.resourceType"
    )
    @Transactional(rollbackFor = Exception.class)
    public void collect(CollectionDTO collectionDTO) {
        Collection collection = Collection.builder()
                .userId(collectionDTO.getUserId())
                .resourceId(collectionDTO.getResourceId())
                .resourceType(collectionDTO.getResourceType())
                .isCollected(true)
                .build();

        collectionMapper.upsert(collection);

        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(collection.getId())
                .resourceType(collectionDTO.getResourceType())
                .statsType(StatsType.COLLECTION_COUNT)
                .operationType(OperationType.COUNT_INCREASE)
                .build();

        eventPublisher.publish(event);
    }

    /**
     * 取消收藏
     */
    @CacheEvict(
            value = "collection::",
            key = "'isCollected::userId=' + #collectionDTO.userId + '&resourceId=' + #collectionDTO.resourceId + '&resourceType=' + #collectionDTO.resourceType"
    )
    public void uncollect(CollectionDTO collectionDTO) {
        Collection collection = Collection.builder()
                .userId(collectionDTO.getUserId())
                .resourceId(collectionDTO.getResourceId())
                .resourceType(collectionDTO.getResourceType())
                .isCollected(false)
                .build();

        collectionMapper.upsert(collection);

        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(collection.getId())
                .resourceType(collectionDTO.getResourceType())
                .statsType(StatsType.COLLECTION_COUNT)
                .operationType(OperationType.COUNT_DECREASE)
                .build();

        eventPublisher.publish(event);
    }

}