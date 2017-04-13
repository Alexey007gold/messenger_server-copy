package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;

/**
 * Created by alex on 31.03.17.
 */
public class UpdateConversationSettingsResponse implements ResponseData {
    private static final long serialVersionUID = 3236434200026328596L;

    private ConversationSettingsDTO conversationSettings;

    public UpdateConversationSettingsResponse(ConversationSettingsDTO conversationSettings) {
        this.conversationSettings = conversationSettings;
    }
}
