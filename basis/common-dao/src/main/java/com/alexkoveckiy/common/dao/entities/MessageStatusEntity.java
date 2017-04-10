package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 30.03.17.
 */

@Entity
@Table(name = "message_status")
public class MessageStatusEntity extends BaseEntity {
    private static final long serialVersionUID = 1723432063152514723L;

    @Column(name = "message_id")
    private String messageId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "conversation_id")
    private String conversationId;
    @Column(name = "status")
    private MessageStatus status;

    public MessageStatusEntity() {
    }

    public MessageStatusEntity(String messageId, String userId, String conversationId, MessageStatus status) {
        this.messageId = messageId;
        this.userId = userId;
        this.conversationId = conversationId;
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public enum MessageStatus {
        CREATED,
        SENT,
        SERVER_RECEIVED,
        DELIVERED,
        READ,
        DELETED
    }
}
