package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.model.entity.Rating;

public interface RatingMessageService {

    /**
     * 发送评分数递增消息
     */
    void sendRatingCountIncreaseMessage(Rating rating);

}
