package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.MessageEntity;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
public interface MessageRepository extends BaseRepository<MessageEntity> {
    List<MessageEntity> findByConversationId(String conversationId);
}
