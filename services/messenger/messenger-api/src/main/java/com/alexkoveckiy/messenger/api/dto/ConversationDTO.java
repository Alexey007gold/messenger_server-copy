package com.alexkoveckiy.messenger.api.dto;

import com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by alex on 30.03.17.
 */
public class ConversationDTO {
    private String id;
    private String name;
    private ConversationType type;
    private Long lastMessageIndex;

    public ConversationDTO() {
    }

    public ConversationDTO(String id, String name, ConversationType type, Long lastMessageIndex) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.lastMessageIndex = lastMessageIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static class ConversationDTOByLastMessageTimeComparator implements Comparator<ConversationDTO> {

        private Map<ConversationDTO, Long> conversationDTOLastMessageTimeMap;

        public ConversationDTOByLastMessageTimeComparator(Map<ConversationDTO, Long> conversationDTOLastMessageTimeMap) {
            this.conversationDTOLastMessageTimeMap = conversationDTOLastMessageTimeMap;
        }

        @Override
        public int compare(ConversationDTO conversationDTO1, ConversationDTO conversationDTO2) {
            return conversationDTOLastMessageTimeMap.get(conversationDTO2) -
                    conversationDTOLastMessageTimeMap.get(conversationDTO1) < 0 ?
                    -1 : 1;
        }
    }
}
