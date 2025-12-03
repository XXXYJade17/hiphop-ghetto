package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicCreateDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicInfoDTO;
import com.xxxyjade.hiphopghetto.pojo.dto.TopicUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicInfoVO;
import com.xxxyjade.hiphopghetto.pojo.vo.TopicVO;

public interface ITopicService {

    /**
     * 分页查询话题
     */
    PageVO<TopicVO> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询话题详情
     */
    TopicInfoVO info(TopicInfoDTO topicInfoDTO);

    /**
     * 创建话题
     */
    void create(TopicCreateDTO topicCreateDTO);

    /**
     * 删除话题
     */
    void delete(Long id);

    /**
     * 更新话题
     */
    void update(TopicUpdateDTO topicUpdateDTO);

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
    void increaseCommentCount(Long id);

    /**
     * 评论数递减
     */
    void decreaseCommentCount(Long id);

    /**
     * 浏览数递增
     */
    void increaseViewCount(Long id);

}
