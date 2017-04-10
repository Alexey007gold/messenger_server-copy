package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationSettingsEntity;
import com.alexkoveckiy.common.dao.service.ConversationService;
import com.alexkoveckiy.common.dao.service.ConversationSettingsService;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import com.alexkoveckiy.messenger.api.message.UpdateConversationSettingsRequest;
import com.alexkoveckiy.messenger.api.message.UpdateConversationSettingsResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.dao.entities.ConversationEntity.ConversationType.INDIVIDUAL;
import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.FORBIDDEN;
import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.NOT_FOUND;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class UpdateConversationSettingsRequestHandler extends MessengerRequestHandler<UpdateConversationSettingsRequest, UpdateConversationSettingsResponse> {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "update_conversation_settings";
    }

    @Override
    protected Response<UpdateConversationSettingsResponse> process(Request<UpdateConversationSettingsRequest> msg) throws Exception {
        if (conversationService.findOne(msg.getData().getConversationSettings().getConversationId()).getType().equals(INDIVIDUAL))
            return ResponseFactory.createResponse(msg, FORBIDDEN, "You can't change settings of individual conversation");

        ConversationSettingsEntity conversationSettingsEntity =
                conversationSettingsService.findByConversationId(msg.getData().getConversationSettings().getConversationId());

        if (conversationSettingsEntity == null)
            return ResponseFactory.createResponse(msg, NOT_FOUND, "Conversation settings not found");
        if (conversationSettingsEntity.getAdminId() != null && !conversationSettingsEntity.getAdminId().equals(msg.getRoutingData().getProfileId()))
            return ResponseFactory.createResponse(msg, FORBIDDEN, "You are not admin of this conversation");

        modelMapperService.map(msg.getData().getConversationSettings(), conversationSettingsEntity);
        conversationSettingsService.save(conversationSettingsEntity);

        UpdateConversationSettingsResponse data = new UpdateConversationSettingsResponse(msg.getData().getConversationSettings());

        return ResponseFactory.createResponse(msg, data);
    }
}
