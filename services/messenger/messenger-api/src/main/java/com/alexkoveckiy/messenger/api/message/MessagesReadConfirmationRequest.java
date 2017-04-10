package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class MessagesReadConfirmationRequest implements RequestData {
    private static final long serialVersionUID = -5798896545582186900L;

    private String conversationId;
    private List<String> messagesIds;

    public MessagesReadConfirmationRequest() {
    }

    public MessagesReadConfirmationRequest(String conversationId, List<String> messagesIds) {
        this.conversationId = conversationId;
        this.messagesIds = messagesIds;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<String> getMessagesIds() {
        return messagesIds;
    }
}
