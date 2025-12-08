package com.xxxyjade.hiphopghetto.service.impl;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.repository.SongRepository;
import com.xxxyjade.hiphopghetto.service.ISongService;
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
public class SongService implements ISongService {

    private final SongRepository songRepository;

    /**
     * （条件）分页查询歌曲
     */
    @ThreeLevelCache(
            key = "'songPage::page=' + #pageQueryDTO.page + '&size=' + #pageQueryDTO.size + '&sortType=' + #pageQueryDTO.sortType"
    )
    @Transactional(rollbackFor = Exception.class)
    public PageVO<Song> page(MusicPageQueryDTO musicPageQueryDTO) {
        Sort sort = Sort.by(Sort.Direction.DESC, "stats." + musicPageQueryDTO.getSortType().getType());
        PageRequest pageRequest = PageRequest.of(
                musicPageQueryDTO.getPage(),
                musicPageQueryDTO.getSize(),
                sort);
        Page<Song> songPage = songRepository.page(pageRequest);
        return new PageVO<>(songPage.getTotalElements(), songPage.getContent());

    }

    /**
     * 查询歌曲详情
     */
    @ThreeLevelCache(
            key = "'songDetail::id=' + #id"
    )
    @Transactional(rollbackFor = Exception.class)
    public Song detail(String id) {
        Optional<Song> song = songRepository.findById(id);
        return song.orElse(null);
    }

    /**
     * 插入歌曲，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "albumSongs::")
    public void saveAll(List<Song> songs) {
        songRepository.saveAll(songs);
    }

    /**
     * 收藏数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseCollectionCount(String id) {
        songRepository.increaseCollectionCount(id);
    }

    /**
     * 收藏数递减
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void decreaseCollectionCount(String id) {
        songRepository.decreaseCollectionCount(id);
    }

    /**
     * 评分数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseRatingCount(String id) {
        songRepository.increaseRatingCount(id);
    }

    /**
     * 评论数递增
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void increaseCommentCount(String id) {
        songRepository.increaseCommentCount(id);
    }

    /**
     * 评论数递减
     */
    @ThreeLevelCacheEvict(key = "'songDetail::id=' + #id")
    public void decreaseCommentCount(String id) {
        songRepository.decreaseCommentCount(id);
    }

}
