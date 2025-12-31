package com.ccit.area.sales.service.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSocketServiceImpl {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 主动发送消息到指定主题。
     */
    public void sendToTopic(String topic,String message) {
        log.info("正在发送消息到主题 {}: {}", topic, message);
        messagingTemplate.convertAndSend(topic, message);
    }
}
