package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ConversationSettingsService;
import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.GetConversationsSettingsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationsSettingsResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class GetConversationsSettingsRequestHandler extends MessengerRequestHandler<GetConversationsSettingsRequest, GetConversationsSettingsResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_conversations_settings";
    }

    @Override
    protected Response<GetConversationsSettingsResponse> process(Request<GetConversationsSettingsRequest> msg) {
        List<ConversationSettingsDTO> conversationSettingsDTOS = new ArrayList<>();
        for (String conversationId : msg.getData().getConversationsIds()) {
            if (!conversationMembershipCheckService.isMember(conversationId, msg.getRoutingData().getProfileId()))
                continue;

            conversationSettingsDTOS.add(modelMapperService.map(conversationSettingsService.findByConversationId(conversationId),
                    ConversationSettingsDTO.class));
        }

        return ResponseFactory.createResponse(msg, new GetConversationsSettingsResponse(conversationSettingsDTOS));
    }
}
