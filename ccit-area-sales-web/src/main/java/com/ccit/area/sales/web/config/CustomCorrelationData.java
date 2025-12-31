package com.ccit.area.sales.web.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;

public class CustomCorrelationData extends CorrelationData {
    private final Object messageContent;

    public CustomCorrelationData(String id, Object messageContent) {
        super(id);
        this.messageContent = messageContent;
    }

    public Object getMessageContent() {
        return messageContent;
    }
}
