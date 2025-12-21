package com.xxxyjade.hiphopghetto.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.mapper.TopicMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@AllArgsConstructor
public class TopicService {

    private final TopicMapper topicMapper;

    /**
     * 分页查询话题
     */
    @Cacheable(
            value = "topicPage::",
            key = "'offset=' + #pageQueryDTO.offset + " +
                  "'&limit=' + #pageQueryDTO.limit + " +
                  "'&sort=' + #pageQueryDTO.sort"
    )
    public PageVO<Topic> page(TopicPageQueryDTO pageQueryDTO) {
        Page<Topic> page = new Page<>(
                pageQueryDTO.getOffset(),
                pageQueryDTO.getLimit()
        );
        if (pageQueryDTO.getSort() != null) {
            page.setOrders(Collections.singletonList(OrderItem.desc(pageQueryDTO.getSort().toString())));
        }
        LambdaQueryWrapper<Topic> wrapper = new LambdaQueryWrapper<Topic>()
                .select(Topic::getId, Topic::getUserId, Topic::getNickname, Topic::getTitle, Topic::getCoverUrl, Topic::getLikeCount);

        page = topicMapper.selectPage(page, wrapper);

        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    @Cacheable(
            value = "topicDetail::",
            key = "'id=' + #topic.id"
    )
    public Topic detail(Topic topic) {
        // TODO 发送浏览量递增消息
        return topicMapper.selectById(topic.getId());
    }

    /**
     * 创建话题
     */
    @ThreeLevelCacheEvict(keyPrefix = "topicPage::")
    public void create(Topic topic) {
        topicMapper.insert(topic);
    }

    /**
     * 删除话题
     */
    @ThreeLevelCacheEvict(
            key = "'topicDetail::id=' + #id",
            keyPrefix = "topicPage::")
    public void delete(Long id) {
        topicMapper.deleteById(id);
    }

    /**
     * 更新话题
     */
    @ThreeLevelCacheEvict(
            key = "'topicDetail::id=' + #topicUpdateDTO.id",
            keyPrefix = "topicPage::")
    public void update(Topic topic) {
        topicMapper.updateById(topic);
    }

    @ThreeLevelCacheEvict(
            key = "'topicDetail::id=' + #statsUpdateDTO.id",
            keyPrefix = "topicPage::")
    public void updateStats(StatsUpdateDTO statsUpdateDTO) {
        topicMapper.updateStats(statsUpdateDTO.getId(), statsUpdateDTO.getStatsType(), statsUpdateDTO.getValue());
    }

}
