package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType;
import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 30.03.17.
 */
public class GetConversationsRequest implements RequestData {
    private static final long serialVersionUID = 1081772319870961712L;

    private ConversationType type;

    public ConversationType getType() {
        return type;
    }
}
