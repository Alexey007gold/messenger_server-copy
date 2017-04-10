package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsByIdsRequest implements RequestData {
    private static final long serialVersionUID = -5673032299058695367L;

    private List<String> conversations_ids;

    public GetConversationsByIdsRequest() {
    }

    public GetConversationsByIdsRequest(List<String> conversations_ids) {
        this.conversations_ids = conversations_ids;
    }

    public List<String> getConversations_ids() {
        return conversations_ids;
    }
}
