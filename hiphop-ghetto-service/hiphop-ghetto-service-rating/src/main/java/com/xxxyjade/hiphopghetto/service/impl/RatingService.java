package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.mapper.RatingMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.RatingDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.SelectScoreDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.IRatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class RatingService implements IRatingService {

    private final RatingMapper ratingMapper;
    private final EventPublisher eventPublisher;
//    private final RatingMessageService ratingMessageService;
//    private final AlbumMapper albumMapper;
//    private final SongMapper songMapper;

    /**
     * 查询用户评分
     */
    @Cacheable(
            value = "rating",
            key = "'rating::userId=' + #selectScoreDTO.userId + '&resourceId=' + #selectScoreDTO.resourceId + '&resourceType=' + #selectScoreDTO.resourceType",
            unless = "#result == null"
    )
    public Integer selectScore(SelectScoreDTO selectScoreDTO) {
        QueryWrapper<Rating> wrapper = new QueryWrapper<Rating>()
                .eq("user_id", selectScoreDTO.getUserId())
                .eq("resource_id", selectScoreDTO.getResourceId())
                .eq("resource_type", selectScoreDTO.getResourceType());
        Rating rating = ratingMapper.selectOne(wrapper);
        return rating.getScore();
    }

    /**
     * 评分
     */
    @CacheEvict(
            value = "rating",
            key = "'rating::userId=' + #ratingDTO.userId + '&resourceId=' + #ratingDTO.resourceId + '&resourceType=' + #ratingDTO.resourceType"
    )
    @Transactional(rollbackFor = Exception.class)
    public void rate(RatingDTO ratingDTO) {
        Rating rating = Rating.builder()
                .userId(ratingDTO.getUserId())
                .resourceId(ratingDTO.getResourceId())
                .resourceType(ratingDTO.getResourceType())
                .score(ratingDTO.getScore())
                .build();

        Wrapper<Rating> queryWrapper = new LambdaQueryWrapper<Rating>()
                .eq(Rating::getUserId, ratingDTO.getUserId())
                .eq(Rating::getResourceId, ratingDTO.getResourceId())
                .eq(Rating::getResourceType, ratingDTO.getResourceType());

        Rating res = ratingMapper.selectOne(queryWrapper);

        if (res != null && res.getScore().equals(ratingDTO.getScore())) {
            return;
        }

        ratingMapper.upsert(rating);

        if (res == null) {
            StatsOperationEvent event = StatsOperationEvent.builder()
                    .id(rating.getId())
                    .resourceType(rating.getResourceType())
                    .statsType(StatsType.RATING_COUNT)
                    .operationType(OperationType.COUNT_INCREASE)
                    .build();
            eventPublisher.publish(event);
        }
    }

}