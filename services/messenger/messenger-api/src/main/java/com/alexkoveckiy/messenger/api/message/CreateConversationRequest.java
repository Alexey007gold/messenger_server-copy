package com.alexkoveckiy.messenger.api.message;

import com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType;
import com.alexkoveckiy.common.protocol.RequestData;

import java.util.Set;

/**
 * Created by alex on 31.03.17.
 */
public class CreateConversationRequest implements RequestData {
    private static final long serialVersionUID = -2615211638278701648L;

    private ConversationType type;
    private Set<String> members_ids;

    public CreateConversationRequest() {
    }

    public CreateConversationRequest(ConversationType type, Set<String> members_ids) {
        this.type = type;
        this.members_ids = members_ids;
    }

    public ConversationType getType() {
        return type;
    }

    public Set<String> getMembers_ids() {
        return members_ids;
    }
}
