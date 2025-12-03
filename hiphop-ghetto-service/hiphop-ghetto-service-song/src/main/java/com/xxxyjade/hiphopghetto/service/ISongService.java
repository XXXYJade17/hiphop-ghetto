package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.pojo.vo.SongDetailVO;

import java.util.List;


public interface ISongService {

    /**
     * （条件）分页查询
     */
    PageVO<Song> page(PageQueryDTO pageQueryDTO);

    /**
     * 查询详情
     */
    SongDetailVO detail(Long id);

    /**
     * 查询专辑中全部歌曲
     */
    List<Song> selectByAlbumId(Long albumId);

    /**
     * 插入歌曲数据，若存在则忽略
     */
    void insertIgnore(Song song);

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
