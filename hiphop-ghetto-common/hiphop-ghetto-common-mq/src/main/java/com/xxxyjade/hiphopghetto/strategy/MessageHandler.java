package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.Message;

public interface MessageHandler<T extends Message> {

    // 消息处理方法
     void handle(T message);

    // 消息类型标识
    Strategy support();

}