package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationEntity;
import com.alexkoveckiy.common.dao.service.ConversationService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.api.message.GetConversationsByIdsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationsByIdsResponse;
import com.alexkoveckiy.messenger.impl.service.ConversationDTOService;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class GetConversationsByIdsRequestHandler extends MessengerRequestHandler<GetConversationsByIdsRequest, GetConversationsByIdsResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationDTOService conversationDTOService;

    @Override
    public String getName() {
        return "get_conversations_by_ids";
    }

    @Override
    protected Response<GetConversationsByIdsResponse> process(Request<GetConversationsByIdsRequest> msg) {
        List<ConversationDTO> conversationDTOS = new ArrayList<>();
        ConversationEntity conversationEntity;
        for (String id : msg.getData().getConversations_ids()) {
            conversationEntity = conversationService.findOne(id);
            if (conversationEntity != null) {
                if (!conversationMembershipCheckService.isMember(conversationEntity.getId(), msg.getRoutingData().getProfileId()))
                    continue;

                conversationDTOS.add(conversationDTOService.getConversationDTO(msg.getRoutingData().getProfileId(), conversationEntity));
            }
        }

        return ResponseFactory.createResponse(msg, new GetConversationsByIdsResponse(conversationDTOS));
    }
}
