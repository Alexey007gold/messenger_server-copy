package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;

/**
 * Created by alex on 31.03.17.
 */
public class CreateConversationResponse implements ResponseData {
    private static final long serialVersionUID = -2447842550541151182L;

    private ConversationDTO conversation;

    public CreateConversationResponse(ConversationDTO conversation) {
        this.conversation = conversation;
    }
}
