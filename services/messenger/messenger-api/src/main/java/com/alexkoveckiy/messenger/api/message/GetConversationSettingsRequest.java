package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationSettingsRequest implements RequestData {
    private static final long serialVersionUID = 9013983801441609120L;

    private String conversationId;

    public GetConversationSettingsRequest() {
    }

    public GetConversationSettingsRequest(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
