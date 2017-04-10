package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.MessageStatusEntity;
import com.alexkoveckiy.common.dao.repositories.MessageStatusRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
@Component
public class MessageStatusService extends BaseService<MessageStatusEntity, MessageStatusRepository> {
    public MessageStatusEntity findByConversationIdAndMessageIdAndUserId(String conversationId, String messageId, String userId) {
        return repository.findByConversationIdAndMessageIdAndUserId(conversationId, messageId, userId);
    }

    public List<MessageStatusEntity> findByConversationId(String conversationId) {
        return repository.findByConversationId(conversationId);
    }
}
