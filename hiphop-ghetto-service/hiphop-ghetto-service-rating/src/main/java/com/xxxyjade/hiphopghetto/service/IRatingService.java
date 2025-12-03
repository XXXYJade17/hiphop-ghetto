package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.RatingDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.SelectScoreDTO;

public interface IRatingService {

    /**
     * 查询评分
     */
    Integer selectScore(SelectScoreDTO selectScoreDTO);

    /**
     * 用户评分
     */
    void rate(RatingDTO ratingDTO);

}
