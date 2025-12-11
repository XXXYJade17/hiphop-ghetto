package com.xxxyjade.hiphopghetto.message;

import com.xxxyjade.hiphopghetto.constant.RabbitConstant;
import com.xxxyjade.hiphopghetto.enums.MessageType;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlMessage implements Message {

    private Album album;

    private MessageType messageType;

    @Override
    public String getQueue() {
        return RabbitConstant.CRAWl_QUEUE;
    }

    @Override
    public MessageType getMessageType() {
        return this.messageType;
    }
}
