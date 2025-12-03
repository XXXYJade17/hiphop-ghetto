package com.xxxyjade.hiphopghetto.publisher;

import com.xxxyjade.hiphopghetto.event.IEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public <T extends IEvent> void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }

}
