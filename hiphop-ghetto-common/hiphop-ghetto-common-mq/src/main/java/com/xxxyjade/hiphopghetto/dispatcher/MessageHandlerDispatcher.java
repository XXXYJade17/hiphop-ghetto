package com.xxxyjade.hiphopghetto.dispatcher;

import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.message.Message;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandlerDispatcher {

    private final Map<String, MessageHandler> handlerMap;

    public MessageHandlerDispatcher(List<MessageHandler> strategies) {
        this.handlerMap = new HashMap<>();
        for (MessageHandler strategy : strategies) {
            String support = strategy.support();
            if (handlerMap.containsKey(support)) {
                throw new HipHopGhettoFrameworkException("消息类型[" + support + "]存在重复的处理器");
            }
            handlerMap.put(support, strategy);
        }
    }

    public void dispatch(Message message) {
        // 提取消息类型标识
        String support = message.support();
        if (support == null) {
            throw new HipHopGhettoFrameworkException("消息缺少类型标识（message-type）");
        }

        // 查找对应的策略
        MessageHandler handler = handlerMap.get(support);
        if (handler == null) {
            throw new HipHopGhettoFrameworkException("不支持的消息类型：" + support);
        }

        handler.handle(message);
    }
}