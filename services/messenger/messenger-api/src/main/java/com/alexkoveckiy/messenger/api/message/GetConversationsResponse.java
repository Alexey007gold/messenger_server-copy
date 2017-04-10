package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.ConversationDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
public class GetConversationsResponse implements ResponseData {
    private static final long serialVersionUID = 6309743833222476222L;

    private List<ConversationDTO> conversations;

    public GetConversationsResponse() {
    }

    public GetConversationsResponse(List<ConversationDTO> conversations) {
        this.conversations = conversations;
    }

    public List<ConversationDTO> getConversations() {
        return conversations;
    }

    public void setConversations(List<ConversationDTO> conversations) {
        this.conversations = conversations;
    }
}
