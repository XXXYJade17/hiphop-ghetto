package com.xxxyjade.hiphopghetto.service.impl;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.repository.AlbumRepository;
import com.xxxyjade.hiphopghetto.service.IAlbumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AlbumService implements IAlbumService {

    private final AlbumRepository albumRepository;

    /**
     * （条件）分页查询专辑
     */
    @ThreeLevelCache(
            key = "'albumPage::page=' + #pageQueryDTO.page + " +
                    "'&size=' + #pageQueryDTO.size + " +
                    "'&sortType=' + #pageQueryDTO.sortType"
    )
    public PageVO<Album> page(MusicPageQueryDTO pageQueryDTO) {
        Sort sort = Sort.by(Sort.Direction.DESC, pageQueryDTO.getSortType().getType());
        PageRequest pageRequest = PageRequest.of(
                pageQueryDTO.getPage() - 1,
                pageQueryDTO.getSize(),
                sort);
        Page<Album> albumPage = albumRepository.page(pageRequest);
        return new PageVO<>(albumPage.getTotalElements(), albumPage.getContent());
    }

    /**
     * 查询专辑详情
     */
    @ThreeLevelCache(key = "'albumDetail::id=' + #id")
    @Transactional(rollbackFor = Exception.class)
    public Album detail(String id) {
        Optional<Album> album = albumRepository.findById(id);
        return album.orElse(null);
    }

    /**
     * 插入专辑，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "albumPage::")
    public void saveAll(List<Album> albums) {
        albumRepository.saveAll(albums);
    }

    /**
     * 收藏数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseCollectionCount(String id) {
        albumRepository.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void decreaseCollectionCount(String id) {
        albumRepository.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseRatingCount(String id) {
        albumRepository.increaseRatingCount(id);
    }

    /**
     * 评论数递增
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void increaseCommentCount(String id) {
        albumRepository.increaseCommentCount(id);
    }

    /**
     * 评论数递减
     */
    @ThreeLevelCacheEvict(key = "'albumDetail::id=' + #id")
    public void decreaseCommentCount(String id) {
        albumRepository.decreaseCommentCount(id);
    }

}
