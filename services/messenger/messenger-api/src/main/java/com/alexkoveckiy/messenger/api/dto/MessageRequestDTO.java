package com.alexkoveckiy.messenger.api.dto;

import com.alexkoveckiy.common.dao.entities.MessageEntity.MessageType;

/**
 * Created by alex on 31.03.17.
 */
public class MessageRequestDTO {

    private String conversationId;
    private MessageType messageType;
    private String payload;
    private Long creationTime;

    public MessageRequestDTO() {
    }

    public MessageRequestDTO(String conversationId, MessageType messageType, String payload, Long creationTime) {
        this.conversationId = conversationId;
        this.messageType = messageType;
        this.payload = payload;
        this.creationTime = creationTime;
    }

    public String getConversationId() {
        return conversationId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getPayload() {
        return payload;
    }

    public Long getCreationTime() {
        return creationTime;
    }
}
