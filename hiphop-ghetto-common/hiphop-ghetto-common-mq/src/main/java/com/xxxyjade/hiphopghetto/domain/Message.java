package com.xxxyjade.hiphopghetto.domain;

import com.xxxyjade.hiphopghetto.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    /**
     * 消息唯一标识
     */
    @Builder.Default
    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    /**
     * 消息类型标识
     */
    private MessageType type;

    /**
     * 消息体
     */
    private T body;

    /**
     * 队列名称
     */
    private String queue;

}
