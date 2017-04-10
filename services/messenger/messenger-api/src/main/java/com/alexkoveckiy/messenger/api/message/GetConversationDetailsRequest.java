package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationDetailsRequest implements RequestData {
    private static final long serialVersionUID = -4702470712544977204L;

    private String conversationId;

    public GetConversationDetailsRequest() {
    }

    public GetConversationDetailsRequest(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
