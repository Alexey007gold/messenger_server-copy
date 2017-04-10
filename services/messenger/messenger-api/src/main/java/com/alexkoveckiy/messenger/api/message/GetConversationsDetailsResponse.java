package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.ConversationDetailsDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsDetailsResponse implements ResponseData {
    private static final long serialVersionUID = -8011608953936064630L;

    private List<ConversationDetailsDTO> conversationsDetails;

    public GetConversationsDetailsResponse(List<ConversationDetailsDTO> conversationsDetails) {
        this.conversationsDetails = conversationsDetails;
    }
}
