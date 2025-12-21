package com.xxxyjade.hiphopghetto.websocket.interceptor;

import com.xxxyjade.hiphopghetto.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            // 从请求参数中获取JWT token
            String token = servletRequest.getServletRequest().getParameter("token");
            if (token != null && !token.isEmpty()) {
                try {
                    Claims claims = JwtUtil.parseAuthJwt(token);
                    Long userId = Long.valueOf(claims.get("userId").toString());
                    attributes.put("userId", userId);
                    log.info("WebSocket握手成功，用户ID: {}", userId);
                    return true;
                } catch (Exception e) {
                    log.error("JWT解析失败", e);
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 握手后处理
    }

}