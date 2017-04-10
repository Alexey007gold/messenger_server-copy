package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.MessageStatusEntity;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
public interface MessageStatusRepository extends BaseRepository<MessageStatusEntity> {
    MessageStatusEntity findByConversationIdAndMessageIdAndUserId(String conversationId, String messageId, String userId);
    List<MessageStatusEntity> findByConversationId(String conversationId);
}
