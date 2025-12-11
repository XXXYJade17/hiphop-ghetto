package com.xxxyjade.hiphopghetto.message;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyMessage implements Message {

    private Long userId;

    private NotifyType notifyType;

    private MessageType messageType;

    @Override
    public String getQueue() {
        return RabbitConstant.NOTIFY_QUEUE;
    }

    @Override
    public MessageType getMessageType() {
        return this.messageType;
    }
}
