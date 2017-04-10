package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 30.03.17.
 */
@Entity
@Table(name = "conversation")
public class ConversationEntity extends BaseEntity {
    private static final long serialVersionUID = 4394762563121666558L;

    @Column(name = "type")
    private ConversationType type;
    @Column(name = "last_message_index")
    private Long lastMessageIndex;
    @Column(name = "creation_date")
    private Long creationDate;

    public ConversationEntity() {
    }

    public ConversationEntity(ConversationType type, Long lastMessageIndex, Long creationDate) {
        this.type = type;
        this.lastMessageIndex = lastMessageIndex;
        this.creationDate = creationDate;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public Long getLastMessageIndex() {
        return lastMessageIndex;
    }

    public void setLastMessageIndex(Long lastMessageIndex) {
        this.lastMessageIndex = lastMessageIndex;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public enum ConversationType {
        INDIVIDUAL,
        GROUP
    }
}
