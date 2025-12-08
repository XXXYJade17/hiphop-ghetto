package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;

import java.util.List;


public interface ISongService {

    /**
     * （条件）分页查询
     */
    PageVO<Song> page(MusicPageQueryDTO musicPageQueryDTO);

    /**
     * 查询详情
     */
    Song detail(String id);

    /**
     * 插入歌曲数据，若存在则忽略
     */
    void saveAll(List<Song> songs);

    /**
     * 收藏数递增
     */
    void increaseCollectionCount(String id);

    /**
     * 收藏数递减
     */
    void decreaseCollectionCount(String id);

    /**
     * 评分数递增
     */
    void increaseRatingCount(String id);

    /**
     * 评论数递增
     */
    void increaseCommentCount(String id);

    /**
     * 评论数递减
     */
    void decreaseCommentCount(String id);

}
