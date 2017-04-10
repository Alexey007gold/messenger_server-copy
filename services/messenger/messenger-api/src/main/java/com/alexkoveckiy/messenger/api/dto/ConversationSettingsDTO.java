package com.alexkoveckiy.messenger.api.dto;

/**
 * Created by alex on 31.03.17.
 */
public class ConversationSettingsDTO {

    private String conversationId;
    private String adminId;
    private String name;
    private String logoImageUri;

    public ConversationSettingsDTO() {
    }

    public ConversationSettingsDTO(String conversationId, String adminId, String name, String logoImageUri) {
        this.conversationId = conversationId;
        this.adminId = adminId;
        this.name = name;
        this.logoImageUri = logoImageUri;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getName() {
        return name;
    }

    public String getLogoImageUri() {
        return logoImageUri;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogoImageUri(String logoImageUri) {
        this.logoImageUri = logoImageUri;
    }
}
