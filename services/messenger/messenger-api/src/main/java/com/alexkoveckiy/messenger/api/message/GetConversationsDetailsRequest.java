package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsDetailsRequest implements RequestData {
    private static final long serialVersionUID = -234417475157834586L;

    private List<String> conversationsIds;

    public GetConversationsDetailsRequest() {
    }

    public GetConversationsDetailsRequest(List<String> conversationsIds) {
        this.conversationsIds = conversationsIds;
    }

    public List<String> getConversationsIds() {
        return conversationsIds;
    }
}
