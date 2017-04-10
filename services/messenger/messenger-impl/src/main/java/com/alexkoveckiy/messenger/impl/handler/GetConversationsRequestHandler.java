package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationEntity;
import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.MessageEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ConversationService;
import com.alexkoveckiy.common.dao.service.MessageService;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO.ConversationDTOByLastMessageTimeComparator;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.GetConversationsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationsResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by alex on 30.03.17.
 */
@Component
public class GetConversationsRequestHandler extends MessengerRequestHandler<GetConversationsRequest, GetConversationsResponse> {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ConversationDTOService conversationDTOService;

    @Override
    public String getName() {
        return "get_conversations";
    }

    @Override
    protected Response<GetConversationsResponse> process(Request<GetConversationsRequest> msg) {
        List<ConversationDTO> conversationDTOS = new ArrayList<>();
        List<ConversationMemberEntity> conversationMemberEntities = conversationMemberService.findByProfileId(msg.getRoutingData().getProfileId());
        ConversationEntity conversationEntity;
        for (ConversationMemberEntity conversationMemberEntity : conversationMemberEntities) {
            conversationEntity = conversationService.findOne(conversationMemberEntity.getConversationId());
            if (conversationEntity != null &&
                    (msg.getData().getType() == null || Objects.equals(conversationEntity.getType(), msg.getData().getType()))) {
                conversationDTOS.add(conversationDTOService.getConversationDTO(msg.getRoutingData().getProfileId(), conversationEntity));
            }
        }

        //sort in the order of last message time
        Map<ConversationDTO, Long> conversationDTOLastMessageTimeMap = new HashMap<>();
        List<MessageEntity> messageEntities;
        for (ConversationDTO conversationDTO : conversationDTOS) {
            messageEntities = messageService.findByConversationId(conversationDTO.getId());
            Collections.sort(messageEntities);
            conversationDTOLastMessageTimeMap.put(conversationDTO, messageEntities.get(messageEntities.size() - 1).getServerReceivedDate());
        }
        conversationDTOS.sort(new ConversationDTOByLastMessageTimeComparator(conversationDTOLastMessageTimeMap));

        return ResponseFactory.createResponse(msg, new GetConversationsResponse(conversationDTOS));
    }
}
