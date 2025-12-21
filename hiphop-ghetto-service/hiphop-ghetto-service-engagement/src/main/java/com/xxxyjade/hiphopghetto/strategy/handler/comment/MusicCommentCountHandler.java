package com.xxxyjade.hiphopghetto.strategy.handler.comment;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.service.MusicStatsService;
import com.xxxyjade.hiphopghetto.strategy.CommentCountStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MusicCommentCountHandler implements CommentCountStrategy {

    private final MusicStatsService musicStatsService;

    @Override
    public void handle(StatsUpdateDTO statsUpdateDTO) {
        musicStatsService.updateStats(statsUpdateDTO);
    }

    @Override
    public List<ResourceType> supports() {
        return List.of(ResourceType.ALBUM, ResourceType.SONG);
    }

}
