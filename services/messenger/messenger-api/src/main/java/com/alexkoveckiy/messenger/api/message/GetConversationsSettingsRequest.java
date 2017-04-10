package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
public class GetConversationsSettingsRequest implements RequestData {
    private static final long serialVersionUID = 5425586062916313408L;

    private List<String> conversationsIds;

    public GetConversationsSettingsRequest() {
    }

    public GetConversationsSettingsRequest(List<String> conversationsIds) {
        this.conversationsIds = conversationsIds;
    }

    public List<String> getConversationsIds() {
        return conversationsIds;
    }
}
