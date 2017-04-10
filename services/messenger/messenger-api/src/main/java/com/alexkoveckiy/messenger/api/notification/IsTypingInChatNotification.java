package com.alexkoveckiy.messenger.api.notification;

import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * Created by alex on 03.04.17.
 */
public class IsTypingInChatNotification implements ResponseData {
    private static final long serialVersionUID = -3242830564827700061L;

    private String conversationId;

    private String profileId;

    public IsTypingInChatNotification(String conversationId, String profileId) {
        this.conversationId = conversationId;
        this.profileId = profileId;
    }
}
