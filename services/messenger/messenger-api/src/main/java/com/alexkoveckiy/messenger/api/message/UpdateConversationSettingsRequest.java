package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;

/**
 * Created by alex on 31.03.17.
 */
public class UpdateConversationSettingsRequest implements RequestData {
    private static final long serialVersionUID = -1906880477582955807L;

    private ConversationSettingsDTO conversationSettings;

    public UpdateConversationSettingsRequest() {
    }

    public UpdateConversationSettingsRequest(ConversationSettingsDTO conversationSettings) {
        this.conversationSettings = conversationSettings;
    }

    public ConversationSettingsDTO getConversationSettings() {
        return conversationSettings;
    }
}
