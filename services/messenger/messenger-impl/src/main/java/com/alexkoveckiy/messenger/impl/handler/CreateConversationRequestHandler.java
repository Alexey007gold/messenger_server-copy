package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.*;
import com.alexkoveckiy.common.dao.service.*;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.api.message.CreateConversationRequest;
import com.alexkoveckiy.messenger.api.message.CreateConversationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType.GROUP;
import static com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType.INDIVIDUAL;
import static com.alexkoveckiy.common.dao.entities.ConversationMemberEntity.ConversationMemberStatus.ACTIVE;
import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.NOT_ACCEPTABLE;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class CreateConversationRequestHandler extends MessengerRequestHandler<CreateConversationRequest,CreateConversationResponse>{

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private NumberService numberService;

    private final String GROUP_CONVERSATION_NAME = "Group";

    @Override
    public String getName() {
        return "create_conversation";
    }

    @Override
    protected Response<CreateConversationResponse> process(Request<CreateConversationRequest> msg) {
        ConversationEntity tmpConversationEntity;
        for (ConversationMemberEntity conversationMemberEntity : conversationMemberService.findByProfileId(msg.getRoutingData().getProfileId())) {
            tmpConversationEntity = conversationService.findOne(conversationMemberEntity.getConversationId());
            if (tmpConversationEntity.getType().equals(INDIVIDUAL)) {
                for (ConversationMemberEntity memberEntity : conversationMemberService.findByConversationId(tmpConversationEntity.getId())) {
                    if (msg.getData().getMembers_ids().contains(memberEntity.getProfileId()))
                        return ResponseFactory.createResponse(msg, NOT_ACCEPTABLE, "Individual conversation with this user already exists!");
                }
            }
        }
        ConversationEntity conversationEntity = conversationService.save(new ConversationEntity(msg.getData().getType(),
                null, System.currentTimeMillis()));
        String conversationName = msg.getData().getType().equals(GROUP) ? GROUP_CONVERSATION_NAME : null;
        conversationSettingsService.save(new ConversationSettingsEntity(conversationEntity.getId(),
                msg.getData().getType().equals(GROUP) ? msg.getRoutingData().getProfileId() : null,
                conversationName, ""));
        msg.getData().getMembers_ids().add(msg.getRoutingData().getProfileId());
        for (String memberId : msg.getData().getMembers_ids()) {
            conversationMemberService.save(new ConversationMemberEntity(memberId, conversationEntity.getId(), ACTIVE,
                    conversationEntity.getCreationDate(), null));
        }

        ConversationDTO conversationDTO = modelMapperService.map(conversationEntity, ConversationDTO.class);
        if (conversationEntity.getType().equals(GROUP))
            conversationDTO.setName(GROUP_CONVERSATION_NAME);
        else {
            ProfileEntity profileEntity = profileService.findOne(msg.getData().getMembers_ids().toArray(new String[1])[0]);
            List<ContactEntity> contactEntities = contactService.findContactByProfileIdAndNumber(msg.getRoutingData().getProfileId(),
                    profileEntity.getPhoneNumber());
            if (contactEntities.size() == 1)
                conversationDTO.setName(contactEntities.get(0).getName());
            else if (profileEntity.getName() != null && !profileEntity.getName().isEmpty())
                conversationDTO.setName(profileEntity.getName());
            else conversationDTO.setName(profileEntity.getPhoneNumber());
        }

        return ResponseFactory.createResponse(msg, new CreateConversationResponse(conversationDTO));
    }
}
