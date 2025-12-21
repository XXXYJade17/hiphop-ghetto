package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.pojo.dto.StatsUpdateDTO;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import com.xxxyjade.hiphopghetto.service.MusicStatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CollectionRatingCountHandler implements MessageHandler<StatsUpdateMessage> {

    private final MusicStatsService musicStatsService;

    @Override
    public void handle(StatsUpdateMessage message) {
        StatsUpdateDTO.StatsUpdateDTOBuilder builder = StatsUpdateDTO.builder()
                .statsType(message.getStatsType())
                .value(message.getValue());
        if(message.getData() instanceof Album album) {
            builder = builder.id(Long.valueOf(album.getId()))
                    .resourceType(ResourceType.ALBUM);
        } else if (message.getData() instanceof Song song) {
            builder = builder.id(Long.valueOf(song.getId()))
                    .resourceType(ResourceType.SONG);
        }
        musicStatsService.updateStats(builder.build());
    }

    @Override
    public List<Strategy> supports() {
        return List.of(Strategy.COLLECTION_COUNT, Strategy.RATING_COUNT);
    }

}
