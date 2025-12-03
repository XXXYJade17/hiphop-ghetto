package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.domain.Message;
import com.xxxyjade.hiphopghetto.enums.MessageType;

public interface MessageHandler<T> {

    // 消息处理方法
    void handle(Message<T> message);

    // 消息类型标识
    MessageType support();

}