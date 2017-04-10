package com.alexkoveckiy.messenger.api.dto;

import com.alexkoveckiy.profile.api.dto.UserProfileDTO;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class ConversationDetailsDTO {

    private String id;

    private List<UserProfileDTO> members;

    public ConversationDetailsDTO() {
    }

    public ConversationDetailsDTO(String id, List<UserProfileDTO> members) {
        this.id = id;
        this.members = members;
    }
}
