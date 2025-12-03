package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.enums.OperationType;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.enums.StatsType;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import com.xxxyjade.hiphopghetto.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.pojo.vo.CommentVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.publisher.EventPublisher;
import com.xxxyjade.hiphopghetto.service.ICommentService;
import com.xxxyjade.hiphopghetto.service.ILikeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService implements ICommentService {

    private final CommentMapper commentMapper;
    private final ILikeService likeService;
    private final EventPublisher eventPublisher;

    /**
     * （条件）分页查询评论
     */
    @Cacheable(
            value = "commentPage::",
            key = "'parentId='+ #pageQueryDTO.parentId +'&page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sortType=' + #pageQueryDTO.sortType"
    )
    public PageVO<CommentVO> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        // 创建分页对象
        Page<Comment> page = new Page<>(
                pageQueryDTO.getPage(),
                pageQueryDTO.getSize()
        );
        // 设置排序，默认为倒序
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        // 查询
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("parent_id", pageQueryDTO.getParentId());
        commentMapper.selectPage(page, wrapper);
        Map<Long, Boolean> userLikes = likeService.selectBatch(
                pageQueryDTO.getUserId(),
                page.getRecords().stream().map(Comment::getId).toList()
        );
        // 构造VO
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : page.getRecords()) {
            CommentVO commentVO = CommentVO.builder()
                    .id(comment.getId())
                    .userId(comment.getUserId())
                    .parentId(comment.getParentId())
                    .parentType(comment.getParentType())
                    .content(comment.getContent())
                    .replyCount(comment.getReplyCount())
                    .likeCount(comment.getLikeCount())
                    .createTime(comment.getCreateTime())
                    .isLiked(userLikes.get(comment.getId()))
                    .build();
            commentVOList.add(commentVO);
        }
        return new PageVO<>(page.getTotal(), commentVOList);
    }

    /**
     * 创建评论
     */
    @ThreeLevelCacheEvict(keyPrefix = "commentPage::")
    public void create(CommentDTO commentDTO) {
        // 构造实体，并插入
        Comment comment = Comment.builder()
                .userId(commentDTO.getUserId())
                .parentId(commentDTO.getParentId())
                .parentType(commentDTO.getParentType())
                .content(commentDTO.getContent())
                .build();
        commentMapper.insert(comment);
        // 发送事件
        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(comment.getParentId())
                .resourceType(commentDTO.getParentType())
                .statsType(StatsType.COMMENT_COUNT)
                .operationType(OperationType.COUNT_INCREASE)
                .build();
        eventPublisher.publish(event);
    }

    /**
     * 删除评论
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #commentDTO.parentId")
    public void delete(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId())
                .userId(commentDTO.getUserId())
                .parentId(commentDTO.getParentId())
                .build();
        commentMapper.deleteById(comment);
        // 发送事件
        StatsOperationEvent event = StatsOperationEvent.builder()
                .id(comment.getParentId())
                .resourceType(commentDTO.getParentType())
                .statsType(StatsType.COMMENT_COUNT)
                .operationType(OperationType.COUNT_DECREASE)
                .build();
        eventPublisher.publish(event);
    }

    /**
     * 点赞数递增
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #id")
    public void increaseLikeCount(Long id) {
        commentMapper.increaseLikeCount(id);
    }

    /**
     * 点赞数递减
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #id")
    public void decreaseLikeCount(Long id) {
        commentMapper.decreaseLikeCount(id);
    }

    /**
     * 评论数递增
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #id")
    public void increaseReplyCount(Long id) {
        commentMapper.increaseReplyCount(id);
    }

    /**
     * 评论数递减
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #id")
    public void decreaseReplyCount(Long id) {
        commentMapper.decreaseReplyCount(id);
    }

}