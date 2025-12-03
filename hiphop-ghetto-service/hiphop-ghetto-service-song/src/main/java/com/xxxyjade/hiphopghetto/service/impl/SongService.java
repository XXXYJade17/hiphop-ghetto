package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.mapper.SongMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.service.ISongService;
import com.xxxyjade.hiphopghetto.util.RedisUtil;
import com.xxxyjade.hiphopghetto.pojo.vo.SongDetailVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SongService implements ISongService {

    private final RedisUtil redisUtil;
    private final SongMapper songMapper;

    /**
     * （条件）分页查询歌曲
     */
    @ThreeLevelCache(
            key = "'songPage::page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sortType=' + #pageQueryDTO.sortType"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<Song> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        Page<Song> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        songMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询歌曲详情
     */
    @ThreeLevelCache(
            key = "'songDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public SongDetailVO detail(Long id) {
        Song song = songMapper.selectById(id);

        SongDetailVO songDetailVO = new SongDetailVO();
        BeanUtils.copyProperties(song, songDetailVO);
        return songDetailVO;
    }

    /**
     * 查询专辑中全部歌曲
     */
    @ThreeLevelCache(key ="'albumSongs::id' + #albumId")
    public List<Song> selectByAlbumId(Long albumId) {
        return songMapper.selectList(
                new QueryWrapper<Song>()
                        .eq("album_id", albumId)
        );
    }

    /**
     * 插入歌曲，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "albumSongs::")
    public void insertIgnore(Song song) {
        songMapper.insertIgnore(song);
    }

    /**
     * 收藏数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseCollectionCount(Long id) {
        songMapper.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void decreaseCollectionCount(Long id) {
        songMapper.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseRatingCount(Long id) {
        songMapper.increaseRatingCount(id);
    }

    /**
     * 评论数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseCommentCount(Long id) {
        songMapper.increaseCommentCount(id);
    }

    /**
     * 评论数递减
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void decreaseCommentCount(Long id) {
        songMapper.decreaseCommentCount(id);
    }

}
