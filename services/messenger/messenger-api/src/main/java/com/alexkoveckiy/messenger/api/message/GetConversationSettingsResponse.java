package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationSettingsResponse implements ResponseData {
    private static final long serialVersionUID = -2312975899748412079L;

    private ConversationSettingsDTO conversationSettings;

    public GetConversationSettingsResponse(ConversationSettingsDTO conversationSettings) {
        this.conversationSettings = conversationSettings;
    }
}
