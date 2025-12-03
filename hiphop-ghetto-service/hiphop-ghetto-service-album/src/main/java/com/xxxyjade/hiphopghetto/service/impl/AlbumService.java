package com.xxxyjade.hiphopghetto.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.enums.SortType;
import com.xxxyjade.hiphopghetto.mapper.AlbumMapper;
import com.xxxyjade.hiphopghetto.pojo.dto.PageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.service.IAlbumService;
import com.xxxyjade.hiphopghetto.service.ISongService;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;
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
public class AlbumService implements IAlbumService {

    private final AlbumMapper albumMapper;
    private final ISongService ISongService;

    /**
     * （条件）分页查询专辑
     */
    @ThreeLevelCache(
            key = "'albumPage::page=' + #pageQueryDTO.page + " +
                    "'&size=' + #pageQueryDTO.size + " +
                    "'&sortType=' + #pageQueryDTO.sortType"
    )
    public PageVO<Album> page(PageQueryDTO pageQueryDTO) {
        SortType sortType = pageQueryDTO.getSortType();
        Page<Album> page = new Page<>(pageQueryDTO.getPage(), pageQueryDTO.getSize());
        if (sortType != SortType.DEFAULT) {
            page.setOrders(Collections.singletonList(OrderItem.desc(sortType.getType())));
        }
        albumMapper.selectPage(page, null);
        return new PageVO<>(page.getTotal(), page.getRecords());
    }

    /**
     * 查询专辑详情
     */
    @ThreeLevelCache(key = "'albumDetail::id=' + #id")
    @Transactional(rollbackFor = Exception.class)
    public AlbumDetailVO detail(Long id) {
        Album album = albumMapper.selectById(id);
        List<Song> songs = ISongService.selectByAlbumId(id);

        AlbumDetailVO albumDetailVO = new AlbumDetailVO();
        BeanUtils.copyProperties(album, albumDetailVO);
        albumDetailVO.setSongs(songs);
        return albumDetailVO;
    }

    /**
     * 插入专辑，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "albumPage::")
    public void insertIgnore(Album album) {
        albumMapper.insertIgnore(album);
    }

    /**
     * 收藏数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseCollectionCount(Long id) {
        albumMapper.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void decreaseCollectionCount(Long id) {
        albumMapper.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseRatingCount(Long id) {
        albumMapper.increaseRatingCount(id);
    }

    /**
     * 评论数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseCommentCount(Long id) {
        albumMapper.increaseCommentCount(id);
    }

    /**
     * 评论数递减
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void decreaseCommentCount(Long id) {
        albumMapper.decreaseCommentCount(id);
    }

}
