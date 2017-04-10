package com.alexkoveckiy.messenger.api.dto;

import com.alexkoveckiy.common.dao.entities.MessageEntity.MessageType;

/**
 * Created by alex on 30.03.17.
 */
public class MessageDTO {

    private String id;
    private String senderId;
    private String conversationId;
    private MessageType type;
    private String payload;
    private Long creationDate;
    private Long serverReceivedDate;

    public MessageDTO() {
    }

    public MessageDTO(String id, String senderId, String conversationId, MessageType type, String payload, Long creationDate, Long serverReceivedDate) {
        this.id = id;
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.type = type;
        this.payload = payload;
        this.creationDate = creationDate;
        this.serverReceivedDate = serverReceivedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public Long getServerReceivedDate() {
        return serverReceivedDate;
    }

    public void setServerReceivedDate(Long serverReceivedDate) {
        this.serverReceivedDate = serverReceivedDate;
    }
}
