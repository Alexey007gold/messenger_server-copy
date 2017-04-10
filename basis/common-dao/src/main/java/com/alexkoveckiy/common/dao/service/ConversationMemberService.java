package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.repositories.ConversationMemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
@Component
public class ConversationMemberService extends BaseService<ConversationMemberEntity, ConversationMemberRepository> {
    public List<ConversationMemberEntity> findByProfileId(String profileId) {
        return repository.findByProfileId(profileId);
    }

    public List<ConversationMemberEntity> findByConversationId(String conversationId) {
        return repository.findByConversationId(conversationId);
    }

    public ConversationMemberEntity findByConversationIdAndProfileId(String conversationId, String profileId) {
        return repository.findByConversationIdAndProfileId(conversationId, profileId);
    }
}
