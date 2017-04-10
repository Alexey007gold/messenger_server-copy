package com.alexkoveckiy.messenger.impl.service;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.MessageEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.dao.entities.ConversationMemberEntity.ConversationMemberStatus.ACTIVE;
import static com.alexkoveckiy.common.dao.entities.ConversationMemberEntity.ConversationMemberStatus.DELETED;

/**
 * Created by alex on 01.04.17.
 */
@Component
public class ConversationMembershipCheckService {

    @Autowired
    private ConversationMemberService conversationMemberService;

    public void checkIsActiveMember(String conversationId, String profileId) throws IllegalAccessException {
        ConversationMemberEntity conversationMemberEntity =
                conversationMemberService.findByConversationIdAndProfileId(conversationId, profileId);
        if (conversationMemberEntity == null || conversationMemberEntity.getStatus().equals(DELETED))
            throw new IllegalAccessException("You are not a member of this conversation");
    }

    public void checkIsMember(String conversationId, String profileId) throws IllegalAccessException {
        ConversationMemberEntity conversationMemberEntity =
                conversationMemberService.findByConversationIdAndProfileId(conversationId, profileId);
        if (conversationMemberEntity == null)
            throw new IllegalAccessException("You are not a member of this conversation");
    }

    public boolean isMember(String conversationId, String profileId) {
        return conversationMemberService.findByConversationIdAndProfileId(conversationId, profileId) != null;
    }

    public boolean isActiveMember(String conversationId, String profileId) {
        ConversationMemberEntity conversationMemberEntity = conversationMemberService.findByConversationIdAndProfileId(conversationId, profileId);
        return conversationMemberEntity != null && conversationMemberEntity.getStatus().equals(ACTIVE);
    }

    public boolean canReadMessage(ConversationMemberEntity conversationMemberEntity, MessageEntity messageEntity) {
        return messageEntity.getServerReceivedDate() >= conversationMemberEntity.getJoinDate() &&
                (conversationMemberEntity.getStatus().equals(ACTIVE) ||
                        messageEntity.getServerReceivedDate() < conversationMemberEntity.getRemoveDate());
    }
}
