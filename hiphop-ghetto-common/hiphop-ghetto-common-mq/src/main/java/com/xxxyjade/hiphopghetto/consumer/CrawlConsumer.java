package com.xxxyjade.hiphopghetto.consumer;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.dispatcher.MessageHandlerDispatcher;
import com.xxxyjade.hiphopghetto.message.CrawlMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CrawlConsumer {

    private final MessageHandlerDispatcher dispatcher;

    @RabbitListener(queues = RabbitConstant.CRAWl_QUEUE)
    public void handlerCrawlMessage(CrawlMessage message) {
        log.info("收到消息: {}", message);
        dispatcher.dispatch(message);
    }

}
