package com.xxxyjade.hiphopghetto.listener;

import com.xxxyjade.hiphopghetto.dispatcher.StatsOperationHandlerDispatcher;
import com.xxxyjade.hiphopghetto.event.StatsOperationEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StatsOperationEventListener {

    private StatsOperationHandlerDispatcher dispatcher;

    @EventListener
    public void handle(StatsOperationEvent event) {
        dispatcher.dispatch(event);
    }

}
