package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 31.03.17.
 */
public class DeleteConversationRequest implements RequestData {
    private static final long serialVersionUID = -4881651219893931993L;

    private String conversationId;

    public DeleteConversationRequest() {
    }

    public DeleteConversationRequest(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
