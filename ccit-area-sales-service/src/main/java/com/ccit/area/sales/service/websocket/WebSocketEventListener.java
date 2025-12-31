package com.ccit.area.sales.service.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@Slf4j
public class WebSocketEventListener {

   @Autowired
   private WebSocketServiceImpl webSocketService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // 发送连接成功的消息给所有订阅了特定主题的客户端
        String sessionId = headerAccessor.getSessionId();

        String topic = "/topic/bidding/connect-status"; // 定义一个用于通知连接状态的主题
        String message = "WebSocket连接成功！Session ID: " + sessionId;
        webSocketService.sendToTopic(topic, message);
        log.info("WebSocket消息发送成功: topic={}, message={}", topic, message);

        // 或者你可以选择直接发给当前连接的用户
        // messagingTemplate.convertAndSendToUser(sessionId, "/queue/connect-status", "WebSocket连接成功！");
    }
}
