package com.xxxyjade.hiphopghetto.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {

    private Long userId;
    private Long notificationId;
    private String content;
    private LocalDateTime createTime;

}
