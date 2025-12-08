package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;

import java.util.List;

public interface IAlbumService {

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(MusicPageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    Album detail(String id);

    /**
     * 插入专辑，若存在则忽略
     */
    void saveAll(List<Album> albums);

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
