package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.mapper.TopicMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicCreateDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicInfoDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Topic;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicInfoVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicVO;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.ILikeService;
import com.xxxyjade.hiphopghetto.service.ITopicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class TopicService implements ITopicService {

    private final ILikeService likeService;
    private final TopicMapper topicMapper;
    private final EventPublisher eventPublisher;

    /**
     * 分页查询话题
     */
    @Cacheable(
            value = "topicPage::",
            key = "'page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sortType=' + #pageQueryDTO.sortType"
    )
    public PageVO<TopicVO> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        // 创建分页对象
        Page<Topic> page = new Page<>(
                pageQueryDTO.getPage(),
                pageQueryDTO.getSize()
        );
        // 设置排序，默认为倒序
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        topicMapper.selectPage(page, null);
        List<TopicVO> topicVOList = new ArrayList<>();
        for (Topic topic : page.getRecords()) {
            TopicVO topicVO = TopicVO.builder()
                    .id(topic.getId())
                    .title(topic.getTitle())
                    .coverUrl(topic.getCoverUrl())
                    .build();
            topicVOList.add(topicVO);
        }
        return new PageVO<>(page.getTotal(), topicVOList);
    }

    @Cacheable(
            value = "topicDetail::",
            key = "'id=' + #topicInfoDTO.id"
    )
    public TopicInfoVO info(TopicInfoDTO topicInfoDTO) {
        Topic topic = topicMapper.selectById(topicInfoDTO.getId());


        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(topic.getId())
                .resourceType(ResourceType.TOPIC)
                .statsType(StatsType.VIEW_COUNT)
                .operationType(OperationType.COUNT_INCREASE)
                .build();

        eventPublisher.publish(event);

        Map<Long, Boolean> userLike = likeService.selectBatch(topicInfoDTO.getUserId(), List.of(topic.getId()));
        return TopicInfoVO.builder()
                .userId(topic.getUserId())
                .title(topic.getTitle())
                .content(topic.getContent())
                .coverUrl(topic.getCoverUrl())
                .viewCount(topic.getViewCount())
                .commentCount(topic.getCommentCount())
                .likeCount(topic.getLikeCount())
                .createTime(topic.getCreateTime())
                .isLiked(userLike.get(topicInfoDTO.getUserId()))
                .build();
    }

    /**
     * 创建话题
     */
    @ThreeLevelCacheEvict(keyPrefix = "topicPage::")
    public void create(TopicCreateDTO topicCreateDTO) {
        Topic topic = Topic.builder()
                .userId(topicCreateDTO.getUserId())
                .title(topicCreateDTO.getTitle())
                .content(topicCreateDTO.getContent())
                .coverUrl(topicCreateDTO.getCoverUrl())
                .build();
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
    public void update(TopicUpdateDTO topicUpdateDTO) {
        Topic topic = Topic.builder()
                .id(topicUpdateDTO.getId())
                .content(topicUpdateDTO.getContent())
                .coverUrl(topicUpdateDTO.getCoverUrl())
                .build();
        topicMapper.updateById(topic);
    }

    /**
     * 点赞数递增
     */
    @CacheEvict(
            value = "topicDetail::",
            key = "'id=' + #id"
    )
    public void increaseLikeCount(Long id) {
        topicMapper.increaseLikeCount(id);
    }

    /**
     * 点赞数递减
     */
    @CacheEvict(
            value = "topicDetail::",
            key = "'id=' + #id"
    )
    public void decreaseLikeCount(Long id) {
        topicMapper.decreaseLikeCount(id);
    }

    /**
     * 评论数递增
     */
    @CacheEvict(
            value = "topicDetail::",
            key = "'id=' + #id"
    )
    public void increaseCommentCount(Long id) {
        topicMapper.increaseCommentCount(id);
    }

    /**
     * 评论数递减
     */
    @CacheEvict(
            value = "topicDetail::",
            key = "'id=' + #id"
    )
    public void decreaseCommentCount(Long id) {
        topicMapper.decreaseCommentCount(id);
    }

    /**
     * 浏览数递增
     */
    @CacheEvict(
            value = "topicDetail::",
            key = "'id=' + #id"
    )
    public void increaseViewCount(Long id) {
        topicMapper.increaseViewCount(id);
    }

}
