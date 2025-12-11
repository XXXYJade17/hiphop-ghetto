package com.xxxyjade.hiphopghetto.service;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.repository.MusicStatsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MusicStatsService {

    private final MusicStatsRepository musicStatsRepository;

    /**
     * 更新专辑统计信息
     */
    @ThreeLevelCacheEvict(key = "#statsUpdateDTO.type + 'Detail::id=' + #id")
    public int updateStats(StatsUpdateDTO statsUpdateDTO) {
        return musicStatsRepository.updateStats(statsUpdateDTO);
    }

}
