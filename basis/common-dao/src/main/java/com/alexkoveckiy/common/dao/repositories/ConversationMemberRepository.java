package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
public interface ConversationMemberRepository extends BaseRepository<ConversationMemberEntity> {
    List<ConversationMemberEntity> findByProfileId(String profileId);
    List<ConversationMemberEntity> findByConversationId(String conversationId);
    ConversationMemberEntity findByConversationIdAndProfileId(String conversationId, String profileId);
}
