package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.messenger.api.dto.ConversationDetailsDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.GetConversationDetailsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationDetailsResponse;
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
public class GetConversationDetailsRequestHandler extends MessengerRequestHandler<GetConversationDetailsRequest, GetConversationDetailsResponse> {

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
        return "get_conversation_details";
    }

    @Override
    protected Response<GetConversationDetailsResponse> process(Request<GetConversationDetailsRequest> msg) throws IllegalAccessException {
        conversationMembershipCheckService.checkIsActiveMember(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        List<UserProfileDTO> userProfileDTOS = new ArrayList<>();
        for (ConversationMemberEntity conversationMemberEntity : conversationMemberService.findByConversationId(msg.getData().getConversationId()))
            userProfileDTOS.add(modelMapperService.map(profileService.findOne(conversationMemberEntity.getProfileId()), UserProfileDTO.class));

        ConversationDetailsDTO conversationDetailsDTO = new ConversationDetailsDTO(msg.getData().getConversationId(), userProfileDTOS);

        return ResponseFactory.createResponse(msg, new GetConversationDetailsResponse(conversationDetailsDTO));
    }
}
