package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 30.03.17.
 */

@Entity
@Table(name = "message")
public class MessageEntity extends BaseEntity implements Comparable<MessageEntity> {
    private static final long serialVersionUID = 6527922527015458048L;

    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "conversation_id")
    private String conversationId;
    @Column(name = "payload")
    private String payload;
    @Column(name = "type")
    private MessageType type;
    @Column(name = "creation_date")
    private Long creationDate;
    @Column(name = "server_received_date")
    private Long serverReceivedDate;

    public MessageEntity() {
    }

    public MessageEntity(String senderId, String conversationId, String payload, MessageType type, Long creationDate, Long serverReceivedDate) {
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.payload = payload;
        this.type = type;
        this.creationDate = creationDate;
        this.serverReceivedDate = serverReceivedDate;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
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

    @Override
    public int compareTo(MessageEntity messageEntity) {
        return serverReceivedDate < messageEntity.getServerReceivedDate() ? -1 : 1;
    }

    public enum MessageType {
        TEXT,
        PICTURE,
        VIDEO,
        AUDIO,
        LINK,
        LOCATION,
        CONTACT,
        STICKER
    }
}
