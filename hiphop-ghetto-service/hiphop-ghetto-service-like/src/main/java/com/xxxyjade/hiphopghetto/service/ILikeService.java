package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.LikeDTO;

import java.util.List;
import java.util.Map;

public interface ILikeService {

    /**
     * 查询点赞状态
     */
    Boolean isLiked(LikeDTO likeDTO);

    /**
     * 点赞
     */
    void like(LikeDTO likeDTO);

    /**
     * 取消点赞
     */
    void cancel(LikeDTO likeDTO);

    /**
     * 批量查询点赞状态
     */
    Map<Long, Boolean> selectBatch(Long userId, List<Long> ids);

}
