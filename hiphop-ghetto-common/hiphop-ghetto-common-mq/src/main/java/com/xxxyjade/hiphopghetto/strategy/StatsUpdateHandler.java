package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StatsUpdateHandler implements MessageHandler<StatsUpdateMessage> {

    @Override
    public void handle(StatsUpdateMessage message) {

    }

    @Override
    public Strategy support() {
        return Strategy.STATS_UPDATE;
    }
}
