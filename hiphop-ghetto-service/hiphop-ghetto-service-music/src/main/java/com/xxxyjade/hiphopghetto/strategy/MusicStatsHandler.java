package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import com.xxxyjade.hiphopghetto.service.MusicStatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MusicStatsHandler implements MessageHandler<StatsUpdateMessage> {

    private final MusicStatsService musicStatsService;

    @Override
    public void handle(StatsUpdateMessage message) {
        musicStatsService.updateStats(message.getStatsUpdateDTO());
    }

    @Override
    public Strategy support() {
        return Strategy.MUSIC_STATS;
    }
}
