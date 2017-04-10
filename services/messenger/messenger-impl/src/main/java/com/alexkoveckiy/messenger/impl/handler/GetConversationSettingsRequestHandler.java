package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationEntity;
import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ConversationService;
import com.alexkoveckiy.common.dao.service.ConversationSettingsService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.messenger.api.dto.ConversationSettingsDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.message.GetConversationSettingsRequest;
import com.alexkoveckiy.messenger.api.message.GetConversationSettingsResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType.INDIVIDUAL;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class GetConversationSettingsRequestHandler extends MessengerRequestHandler<GetConversationSettingsRequest, GetConversationSettingsResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_conversation_settings";
    }

    @Override
    protected Response<GetConversationSettingsResponse> process(Request<GetConversationSettingsRequest> msg) throws IllegalAccessException {
        conversationMembershipCheckService.checkIsMember(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

        ConversationSettingsDTO conversationSettingsDTO =
                modelMapperService.map(conversationSettingsService.findByConversationId(msg.getData().getConversationId()),
                        ConversationSettingsDTO.class);
        ConversationEntity conversationEntity = conversationService.findOne(conversationSettingsDTO.getConversationId());
        if (conversationEntity.getType().equals(INDIVIDUAL)) {
            for (ConversationMemberEntity conversationMemberEntity : conversationMemberService.findByConversationId(conversationSettingsDTO.getConversationId())) {
                if (!conversationMemberEntity.getProfileId().equals(msg.getRoutingData().getProfileId()))
                    conversationSettingsDTO.setLogoImageUri(profileService.findOne(conversationMemberEntity.getProfileId()).getAvatarUri());
            }
        }

        return ResponseFactory.createResponse(msg, new GetConversationSettingsResponse(conversationSettingsDTO));
    }
}
