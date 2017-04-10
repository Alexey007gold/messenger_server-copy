package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 30.03.17.
 */

@Entity
@Table(name = "conversation_settings")
public class ConversationSettingsEntity extends BaseEntity {
    private static final long serialVersionUID = -6851686900723471493L;

    @Column(name = "conversation_id")
    private String conversationId;
    @Column(name = "admin_id")
    private String adminId;
    private String name;
    @Column(name = "logo_image_uri")
    private String logoImageUri;

    public ConversationSettingsEntity() {
    }

    public ConversationSettingsEntity(String conversationId, String adminId, String name, String logoImageUri) {
        this.conversationId = conversationId;
        this.adminId = adminId;
        this.name = name;
        this.logoImageUri = logoImageUri;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoImageUri() {
        return logoImageUri;
    }

    public void setLogoImageUri(String logoImageUri) {
        this.logoImageUri = logoImageUri;
    }
}
