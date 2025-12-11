package com.xxxyjade.hiphopghetto.consumer;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.dispatcher.MessageHandlerDispatcher;
import com.xxxyjade.hiphopghetto.message.StatsUpdateMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class StatsConsumer {

    private final MessageHandlerDispatcher dispatcher;

    @RabbitListener(queues = RabbitConstant.STATS_QUEUE)
    public void handlerCrawlMessage(StatsUpdateMessage message) {
        log.info("收到消息: {}", message);
        dispatcher.dispatch(message);
    }

}
