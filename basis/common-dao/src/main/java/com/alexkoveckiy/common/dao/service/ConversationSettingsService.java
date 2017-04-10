package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ConversationSettingsEntity;
import com.alexkoveckiy.common.dao.repositories.ConversationSettingsRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 30.03.17.
 */
@Component
public class ConversationSettingsService extends BaseService<ConversationSettingsEntity, ConversationSettingsRepository> {
    public ConversationSettingsEntity findByConversationId(String conversationId) {
        return repository.findByConversationId(conversationId);
    }
}
