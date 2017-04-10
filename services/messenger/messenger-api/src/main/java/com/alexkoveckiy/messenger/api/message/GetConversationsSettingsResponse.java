package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsSettingsResponse implements ResponseData {
    private static final long serialVersionUID = -3459488543606530384L;

    private List<ConversationSettingsDTO> conversationsSettings;

    public GetConversationsSettingsResponse(List<ConversationSettingsDTO> conversationsSettings) {
        this.conversationsSettings = conversationsSettings;
    }
}
