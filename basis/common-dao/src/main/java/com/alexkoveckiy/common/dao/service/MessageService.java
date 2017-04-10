package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.MessageEntity;
import com.alexkoveckiy.common.dao.repositories.MessageRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 30.03.17.
 */
@Component
public class MessageService extends BaseService<MessageEntity, MessageRepository> {
    public List<MessageEntity> findByConversationId(String conversationId) {
        return repository.findByConversationId(conversationId);
    }
}
