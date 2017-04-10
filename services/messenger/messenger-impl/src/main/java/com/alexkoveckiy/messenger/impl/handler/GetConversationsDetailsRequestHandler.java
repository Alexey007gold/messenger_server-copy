package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.messenger.api.dto.ConversationDetailsDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.GetConversationsDetailsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationsDetailsResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class GetConversationsDetailsRequestHandler extends MessengerRequestHandler<GetConversationsDetailsRequest, GetConversationsDetailsResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_conversations_details";
    }

    @Override
    protected Response<GetConversationsDetailsResponse> process(Request<GetConversationsDetailsRequest> msg) {
        List<ConversationDetailsDTO> conversationDetailsDTOS = new ArrayList<>();
        List<UserProfileDTO> userProfileDTOS;
        for (String conversationId : msg.getData().getConversationsIds()) {
            if (!conversationMembershipCheckService.isActiveMember(conversationId, msg.getRoutingData().getProfileId()))
                continue;

            userProfileDTOS = new ArrayList<>();
            for (ConversationMemberEntity conversationMemberEntity : conversationMemberService.findByConversationId(conversationId))
                userProfileDTOS.add(modelMapperService.map(profileService.findOne(conversationMemberEntity.getProfileId()), UserProfileDTO.class));
            conversationDetailsDTOS.add(new ConversationDetailsDTO(conversationId, userProfileDTOS));
        }

        return ResponseFactory.createResponse(msg, new GetConversationsDetailsResponse(conversationDetailsDTOS));
    }
}
