package com.xxxyjade.hiphopghetto.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.mapper.CommentMapper;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.CommentPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Comment;
import com.xxxyjade.hiphopghetto.pojo.vo.CommentVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
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
public class CommentService {

    private final CommentMapper commentMapper;
    private final LikeService likeService;

    /**
     * 分页查询评论
     */
    @Cacheable(
            value = "commentPage::",
            key = "'parentId='+ #pageQueryDTO.parentId +" +
                  "'&page=' + #pageQueryDTO.page + " +
                  "'&size=' + #pageQueryDTO.size + " +
                  "'&sortType=' + #pageQueryDTO.sortType"
    )
    public PageVO<CommentVO> page(CommentPageQueryDTO pageQueryDTO) {
        Page<Comment> page = new Page<>(
                pageQueryDTO.getPage() - 1,
                pageQueryDTO.getSize()
        );
        page.setOrders(Collections.singletonList(OrderItem.desc(pageQueryDTO.getSortType().toString())));
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>()
                .eq(Comment::getParentId, pageQueryDTO.getParentId());
        commentMapper.selectPage(page, wrapper);

        Map<Long, Boolean> userLikes = likeService.selectBatch(
                pageQueryDTO.getUserId(),
                page.getRecords().stream().map(Comment::getId).toList());

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
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #comment.rootId")
    public void create(Comment comment) {
        int insert = commentMapper.insert(comment);
        if (insert > 0) {
            // TODO 发送消息
        }
    }

    /**
     * 删除评论
     */
    @ThreeLevelCacheEvict(keyPrefix = "'commentPage::parentId=' + #commentDTO.parentId")
    public void delete(Comment comment) {
        // 构造实体，并删除
        int delete = commentMapper.deleteById(comment);
        if (delete > 0) {
            // TODO 发送消息
        }
    }

    private StatsUpdateMessage buildMessage(Comment comment) {
        // TODO 构造消息
        return null;
    }

}