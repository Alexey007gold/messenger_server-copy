package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ConversationSettingsEntity;

/**
 * Created by alex on 30.03.17.
 */
public interface ConversationSettingsRepository extends BaseRepository<ConversationSettingsEntity> {
    ConversationSettingsEntity findByConversationId(String conversationId);
}
