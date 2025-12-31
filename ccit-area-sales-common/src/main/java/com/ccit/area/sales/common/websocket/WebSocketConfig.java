package com.ccit.area.sales.common.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 配置WebSocket的支持
 * 该类实现了WebSocketConfigurer接口，并通过@EnableWebSocket注解启用WebSocket支持
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // 设置消息订阅路径
        config.setApplicationDestinationPrefixes("/app"); // 客户端发送消息的路径前缀
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-endpoint").setAllowedOriginPatterns("*").withSockJS(); // 不设置allowed origins，默认允许所有来源，但不发送凭证
    }
}
