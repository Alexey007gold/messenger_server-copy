package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsByIdsResponse implements ResponseData {
    private static final long serialVersionUID = 3635533912992977524L;

    private List<ConversationDTO> conversations;

    public GetConversationsByIdsResponse(List<ConversationDTO> conversations) {
        this.conversations = conversations;
    }
}
