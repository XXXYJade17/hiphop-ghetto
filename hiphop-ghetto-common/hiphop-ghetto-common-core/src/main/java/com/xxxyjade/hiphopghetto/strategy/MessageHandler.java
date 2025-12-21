package com.xxxyjade.hiphopghetto.strategy;

import com.xxxyjade.hiphopghetto.enums.Strategy;
import com.xxxyjade.hiphopghetto.message.Message;

import java.util.List;

public interface MessageHandler<T extends Message> {

    // 消息处理方法
     void handle(T message);

    // 消息类型标识
    List<Strategy> supports();

}