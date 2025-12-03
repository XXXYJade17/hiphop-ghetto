package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;

public interface IAlbumService {

    /**
     * （条件）分页查询
     */
    PageVO<Album> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询专辑详情
     */
    AlbumDetailVO detail(Long id);

    /**
     * 插入专辑，若存在则忽略
     */
    void insertIgnore(Album album);

    /**
     * 收藏数递增
     */
    void increaseCollectionCount(Long id);

    /**
     * 收藏数递减
     */
    void decreaseCollectionCount(Long id);

    /**
     * 评分数递增
     */
    void increaseRatingCount(Long id);

    /**
     * 评论数递增
     */
    void increaseCommentCount(Long id);

    /**
     * 评论数递减
     */
    void decreaseCommentCount(Long id);

}
