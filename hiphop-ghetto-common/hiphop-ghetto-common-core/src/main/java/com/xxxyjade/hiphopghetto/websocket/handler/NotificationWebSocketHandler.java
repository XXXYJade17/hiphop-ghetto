package com.xxxyjade.hiphopghetto.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxyjade.hiphopghetto.websocket.message.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    // 存储用户ID与WebSocket会话的映射关系
    private static final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从会话属性中获取用户ID（需要在握手时设置）
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.put(userId, session);
            log.info("用户 {} 建立WebSocket连接", userId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 移除断开的会话
        userSessions.values().remove(session);
        log.info("WebSocket连接已关闭: {}", status);
    }

    /**
     * 向特定用户发送通知
     * @param userId 用户ID
     * @param notification 通知内容
     */
    public static void sendNotificationToUser(Long userId, NotificationMessage notification) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String jsonMessage = new ObjectMapper().writeValueAsString(notification);
                session.sendMessage(new TextMessage(jsonMessage));
                log.info("向用户 {} 发送通知: {}", userId, jsonMessage);
            } catch (Exception e) {
                log.error("发送通知失败", e);
            }
        }
    }

    /**
     * 广播通知给所有在线用户
     * @param notification 通知内容
     */
    public static void broadcastNotification(NotificationMessage notification) {
        userSessions.values().forEach(session -> {
            if (session.isOpen()) {
                try {
                    String jsonMessage = new ObjectMapper().writeValueAsString(notification);
                    session.sendMessage(new TextMessage(jsonMessage));
                } catch (Exception e) {
                    log.error("广播通知失败", e);
                }
            }
        });
    }

}