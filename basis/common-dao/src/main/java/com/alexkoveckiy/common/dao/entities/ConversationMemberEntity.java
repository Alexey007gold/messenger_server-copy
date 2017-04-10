package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 30.03.17.
 */

@Entity
@Table(name = "conversation_member")
public class ConversationMemberEntity extends BaseEntity {
    private static final long serialVersionUID = 9052939425332920493L;

    @Column(name = "profile_id")
    private String profileId;
    @Column(name = "conversation_id")
    private String conversationId;
    private ConversationMemberStatus status;
    @Column(name = "join_date")
    private Long joinDate;
    @Column(name = "remove_date")
    private Long removeDate;

    public ConversationMemberEntity() {
    }

    public ConversationMemberEntity(String profileId, String conversationId, ConversationMemberStatus status, Long joinDate, Long removeDate) {
        this.profileId = profileId;
        this.conversationId = conversationId;
        this.status = status;
        this.joinDate = joinDate;
        this.removeDate = removeDate;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationMemberStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationMemberStatus status) {
        this.status = status;
    }

    public Long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Long joinDate) {
        this.joinDate = joinDate;
    }

    public Long getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Long removeDate) {
        this.removeDate = removeDate;
    }

    public enum ConversationMemberStatus {
        DELETED,
        ACTIVE,
    }
}
