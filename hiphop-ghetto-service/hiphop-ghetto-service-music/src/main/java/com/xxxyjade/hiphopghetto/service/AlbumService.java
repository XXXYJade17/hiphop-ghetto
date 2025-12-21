package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.dto.MusicPageQueryDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.vo.AlbumDetailVO;
import com.xxxyjade.hiphopghetto.pojo.vo.PageVO;
import com.xxxyjade.hiphopghetto.repository.AlbumRepository;
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
public class AlbumService{

    private final AlbumRepository albumRepository;

    /**
     * 分页查询专辑
     */
    @ThreeLevelCache(
            key = "'albumPage::offset=' + #pageQueryDTO.offset + " +
                  "'&limit=' + #pageQueryDTO.limit + " +
                  "'&sort=' + #pageQueryDTO.sort"
    )
    public PageVO<Album> page(MusicPageQueryDTO pageQueryDTO) {
        PageRequest pageRequest = PageRequest.of(
                pageQueryDTO.getOffset() - 1,
                pageQueryDTO.getLimit());
        if (pageQueryDTO.getSort() != null) {
            pageRequest.withSort(Sort.by(Sort.Direction.DESC, pageQueryDTO.getSort().getField()));
        }
        Page<Album> albumPage = albumRepository.page(pageRequest);
        return new PageVO<>(albumPage.getTotalElements(), albumPage.getContent());
    }

    /**
     * 查询专辑详情
     */
    @ThreeLevelCache(key = "'albumDetail::id=' + #id")
    public AlbumDetailVO detail(String id) {
        Optional<AlbumDetailVO> detail = albumRepository.detail(id);
        return detail.orElse(null);
    }

    /**
     * 插入专辑，若存在则忽略
     */
    @ThreeLevelCacheEvict(keyPrefix = "'albumPage::'")
    public void saveAll(List<Album> albums) {
        albumRepository.saveAll(albums);
    }

}
