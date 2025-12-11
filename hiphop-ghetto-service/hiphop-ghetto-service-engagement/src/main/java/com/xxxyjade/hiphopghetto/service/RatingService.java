package com.xxxyjade.hiphopghetto.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.mapper.RatingMapper;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Rating;
import com.xxxyjade.hiphopghetto.sender.MessageSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RatingService {

    private final RatingMapper ratingMapper;
    private final MessageSender messageSender;

    /**
     * 查询评分
     */
    @Cacheable(
            value = "rating",
            key = "'rating::userId=' + #rating.userId + " +
                  "'&resourceId=' + #rating.id + " +
                  "'&resourceType=' + #rating.resourceType",
            unless = "#result == null"
    )
    public Integer select(Rating rating) {
        QueryWrapper<Rating> wrapper = new QueryWrapper<>(rating);
        rating = ratingMapper.selectOne(wrapper);
        return rating.getScore();
    }

    /**
     * 创建/更新评分
     */
    @CacheEvict(
            value = "rating",
            key = "'rating::userId=' + #rating.userId + " +
                  "'&resourceId=' + #rating.resourceId + " +
                  "'&resourceType=' + #rating.resourceType"
    )
    public void save(Rating rating) {
        // TODO 参数校验
        int score = rating.getScore();
        QueryWrapper<Rating> wrapper = new QueryWrapper<>(rating);
        rating = ratingMapper.selectOne(wrapper);
        boolean exist = rating != null;

        if (exist && rating.getScore().equals(score)) {
            return;
        }

        rating.setScore(score);
        boolean upsert = ratingMapper.insertOrUpdate(rating);

        if (exist && upsert) {
            messageSender.send(RabbitConstant.STATS_QUEUE, buildMessage(rating));
        }
    }

    private StatsUpdateMessage buildMessage(Rating rating) {
        StatsUpdateDTO statsUpdateDTO = StatsUpdateDTO.builder()
                .id(rating.getResourceId())
                .resourceType(rating.getResourceType())
                .statsType(StatsType.RATING_COUNT)
                .value(1)
                .build();
        return StatsUpdateMessage.builder()
                .support(Strategy.MUSIC_STATS)
                .statsUpdateDTO(statsUpdateDTO)
                .build();
    }

}