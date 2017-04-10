package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 03.04.17.
 */
public class IsTypingInChatRequest implements RequestData {
    private static final long serialVersionUID = -7079744045933492590L;

    private String conversationId;

    public IsTypingInChatRequest() {
    }

    public IsTypingInChatRequest(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
