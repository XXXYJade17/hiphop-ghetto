package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.repository.SongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    /**
     * 分页查询歌曲
     */
    @ThreeLevelCache(
            key = "'songPage::offset=' + #pageQueryDTO.offset + " +
                    "'&limit=' + #pageQueryDTO.limit + " +
                    "'&sort=' + #pageQueryDTO.sort"
    )
    public PageVO<Song> page(MusicPageQueryDTO pageQueryDTO) {
        PageRequest pageRequest = PageRequest.of(
                pageQueryDTO.getOffset() - 1,
                pageQueryDTO.getLimit());
        if (pageQueryDTO.getSort() != null) {
            pageRequest.withSort(Sort.by(Sort.Direction.DESC, pageQueryDTO.getSort().getField()));
        }
        Page<Song> songPage = songRepository.page(pageRequest);
        return new PageVO<>(songPage.getTotalElements(), songPage.getContent());

    }

    /**
     * 查询歌曲详情
     */
    @ThreeLevelCache(
            key = "'songDetail::id=' + #id"
    )
    public Song detail(String id) {
        Optional<Song> song = songRepository.findById(id);
        return song.orElse(null);
    }

    /**
     * 插入歌曲，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "songPage::")
    public void saveAll(List<Song> songs) {
        songRepository.saveAll(songs);
    }

}
