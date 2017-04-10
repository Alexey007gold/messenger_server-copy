package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.MessageEntity;
import com.alexkoveckiy.common.dao.entities.MessageStatusEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.MessageService;
import com.alexkoveckiy.common.dao.service.MessageStatusService;
import com.alexkoveckiy.messenger.api.dto.MessageDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.ResponseCursor;
import com.alexkoveckiy.messenger.api.message.GetMessagesRequest;
import com.alexkoveckiy.messenger.api.message.GetMessagesResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.alexkoveckiy.common.dao.entities.MessageStatusEntity.MessageStatus.DELIVERED;
import static com.alexkoveckiy.common.dao.entities.MessageStatusEntity.MessageStatus.READ;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class GetMessagesRequestHandler extends MessengerRequestHandler<GetMessagesRequest, GetMessagesResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageStatusService messageStatusService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_messages";
    }

    @Override
    protected Response<GetMessagesResponse> process(Request<GetMessagesRequest> msg) throws IllegalAccessException {
        conversationMembershipCheckService.checkIsMember(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        ConversationMemberEntity conversationMemberEntity =
                conversationMemberService.findByConversationIdAndProfileId(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        List<MessageEntity> messageEntities = messageService.findByConversationId(msg.getData().getConversationId());
        Collections.sort(messageEntities);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        int countTo = msg.getData().getCursor().getOffset() + msg.getData().getCursor().getCount();
        MessageStatusEntity messageStatusEntity;
        for (int i = msg.getData().getCursor().getOffset(); i < countTo; i++) {
            if (i == messageEntities.size()) break;
            if (conversationMembershipCheckService.canReadMessage(conversationMemberEntity, messageEntities.get(i))) {
                messageDTOS.add(modelMapperService.map(messageEntities.get(i), MessageDTO.class));
                messageStatusEntity = messageStatusService.findByConversationIdAndMessageIdAndUserId(msg.getData().getConversationId(),
                        messageEntities.get(i).getId(), msg.getRoutingData().getProfileId());
                if (messageStatusEntity != null && messageStatusEntity.getStatus().compareTo(DELIVERED) < 0) {
                    messageStatusEntity.setStatus(DELIVERED);
                    messageStatusService.save(messageStatusEntity);
                }
            }
        }
        int totalUnread = 0;
        for (MessageEntity messageEntity : messageEntities) {
            messageStatusEntity = messageStatusService.findByConversationIdAndMessageIdAndUserId(msg.getData().getConversationId(),
                    messageEntity.getId(), msg.getRoutingData().getProfileId());
            if (messageStatusEntity != null && conversationMembershipCheckService.canReadMessage(conversationMemberEntity, messageEntity) && messageStatusEntity.getStatus().compareTo(READ) < 0)
                totalUnread++;
        }
        ResponseCursor cursor = new ResponseCursor(messageEntities.size() > countTo);

        return ResponseFactory.createResponse(msg, new GetMessagesResponse(messageDTOS, totalUnread, cursor));
    }
}
