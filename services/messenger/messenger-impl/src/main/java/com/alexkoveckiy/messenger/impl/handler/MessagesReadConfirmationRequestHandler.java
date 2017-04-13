package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.MessageStatusEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.MessageService;
import com.alexkoveckiy.common.dao.service.MessageStatusService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.api.message.MessagesReadConfirmationRequest;
import com.alexkoveckiy.messenger.api.message.MessagesReadConfirmationResponse;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.dao.entities.MessageStatusEntity.MessageStatus.READ;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class MessagesReadConfirmationRequestHandler extends MessengerRequestHandler<MessagesReadConfirmationRequest, MessagesReadConfirmationResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private MessageStatusService messageStatusService;

    @Autowired
    private MessageService messageService;

    @Override
    public String getName() {
        return "messages_read_confirmation";
    }

    @Override
    protected Response<MessagesReadConfirmationResponse> process(Request<MessagesReadConfirmationRequest> msg) throws IllegalAccessException {
        conversationMembershipCheckService.checkIsMember(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        ConversationMemberEntity conversationMemberEntity =
                conversationMemberService.findByConversationIdAndProfileId(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        MessageStatusEntity messageStatusEntity;
        for (String messageId : msg.getData().getMessagesIds()) {
            messageStatusEntity = messageStatusService.findByConversationIdAndMessageIdAndUserId(msg.getData().getConversationId(),
                    messageId, msg.getRoutingData().getProfileId());
            if (messageStatusEntity != null && conversationMembershipCheckService.canReadMessage(conversationMemberEntity, messageService.findOne(messageStatusEntity.getMessageId()))) {
                messageStatusEntity.setStatus(READ);
                messageStatusService.save(messageStatusEntity);
            }
        }

        return ResponseFactory.createResponse(msg, new MessagesReadConfirmationResponse());
    }
}
