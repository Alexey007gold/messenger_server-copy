package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 31.03.17.
 */
public class GetMessagesRequest implements RequestData {
    private static final long serialVersionUID = 180358648180002295L;

    private String conversationId;
    private RequestCursor cursor;

    public GetMessagesRequest() {
    }

    public GetMessagesRequest(String conversationId, RequestCursor cursor) {
        this.conversationId = conversationId;
        this.cursor = cursor;
    }

    public String getConversationId() {
        return conversationId;
    }

    public RequestCursor getCursor() {
        return cursor;
    }
}
