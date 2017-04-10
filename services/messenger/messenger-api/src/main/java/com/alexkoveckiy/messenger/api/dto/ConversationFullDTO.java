package com.alexkoveckiy.messenger.api.dto;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
public class ConversationFullDTO {
    private String id;
    private List<MessageDTO> messages;
    private Long totalUnread;
    private Boolean exists;
    private String name;
    private String logoImageUri;
    private Integer type;
    private Long lastMessageIndex;
    private List<String> membersIds;

    public ConversationFullDTO() {
    }

    public ConversationFullDTO(String id, List<MessageDTO> messages, Long totalUnread, Boolean exists, String name, String logoImageUri, Integer type, Long lastMessageIndex, List<String> membersIds) {
        this.id = id;
        this.messages = messages;
        this.totalUnread = totalUnread;
        this.exists = exists;
        this.name = name;
        this.logoImageUri = logoImageUri;
        this.type = type;
        this.lastMessageIndex = lastMessageIndex;
        this.membersIds = membersIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public Long getTotalUnread() {
        return totalUnread;
    }

    public void setTotalUnread(Long totalUnread) {
        this.totalUnread = totalUnread;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getLastMessageIndex() {
        return lastMessageIndex;
    }

    public void setLastMessageIndex(Long lastMessageIndex) {
        this.lastMessageIndex = lastMessageIndex;
    }

    public List<String> getMembersIds() {
        return membersIds;
    }

    public void setMembersIds(List<String> membersIds) {
        this.membersIds = membersIds;
    }
}
