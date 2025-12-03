package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.CommentDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.CommentVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;

public interface ICommentService {

    /**
     * 分页查询评论
     */
    PageVO<CommentVO> page(PageQueryDTO pageQueryDTO);

    /**
     * 创建评论
     */
    void create(CommentDTO commentDTO);

    /**
     * 删除评论
     */
    void delete(CommentDTO commentDTO);

    /**
     * 点赞数递增
     */
    void increaseLikeCount(Long id);

    /**
     * 点赞数递减
     */
    void decreaseLikeCount(Long id);

    /**
     * 评论数递增
     */
    void increaseReplyCount(Long id);

    /**
     * 评论数递减
     */
    void decreaseReplyCount(Long id);

}
