package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.ConversationDetailsDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationDetailsResponse implements ResponseData {
    private static final long serialVersionUID = 7346927787310656738L;

    private ConversationDetailsDTO conversationDetails;

    public GetConversationDetailsResponse(ConversationDetailsDTO conversationDetails) {
        this.conversationDetails = conversationDetails;
    }
}
