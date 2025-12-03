package com.xxxyjade.hiphopghetto.dispatcher;

import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.strategy.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandlerDispatcher {

    private final Map<MessageType, MessageHandler> strategyMap;

    public MessageHandlerDispatcher(List<MessageHandler> strategies) {
        this.strategyMap = new HashMap<>();
        for (MessageHandler strategy : strategies) {
            MessageType type = strategy.support();
            if (strategyMap.containsKey(type)) {
                throw new HipHopGhettoFrameworkException("消息类型[" + type + "]存在重复的处理器");
            }
            strategyMap.put(type, strategy);
        }
    }

    public void dispatch(Message message) {
        // 提取消息类型标识
        MessageType type = message.getType();
        if (type == null) {
            throw new HipHopGhettoFrameworkException("消息缺少类型标识（message-type）");
        }

        // 查找对应的策略
        MessageHandler strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new HipHopGhettoFrameworkException("不支持的消息类型：" + type);
        }

        strategy.handle(message);
    }
}