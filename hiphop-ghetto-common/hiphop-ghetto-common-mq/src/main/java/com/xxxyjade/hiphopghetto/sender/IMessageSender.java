package com.xxxyjade.hiphopghetto.sender;

import com.xxxyjade.hiphopghetto.domain.Message;

public interface IMessageSender {

    <T> void send(Message<T> message);

}
