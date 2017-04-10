package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.ConversationSettingsEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ConversationSettingsService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import com.alexkoveckiy.messenger.api.message.DeleteConversationRequest;
import com.alexkoveckiy.messenger.api.message.DeleteConversationResponse;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.alexkoveckiy.common.dao.entities.ConversationMemberEntity.ConversationMemberStatus.DELETED;
import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.NOT_ACCEPTABLE;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class DeleteConversationRequestHandler extends MessengerRequestHandler<DeleteConversationRequest, DeleteConversationResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Override
    public String getName() {
        return "delete_conversation";
    }

    @Override
    protected Response<DeleteConversationResponse> process(Request<DeleteConversationRequest> msg) throws IllegalAccessException {
            conversationMembershipCheckService.checkIsActiveMember(msg.getData().getConversationId(), msg.getRoutingData().getProfileId());

            ConversationSettingsEntity conversationSettingsEntity = conversationSettingsService.findByConversationId(msg.getData().getConversationId());
            List<ConversationMemberEntity> conversationMemberEntities = conversationMemberService.findByConversationId(msg.getData().getConversationId());
            if (conversationSettingsEntity.getAdminId() != null &&
                    conversationSettingsEntity.getAdminId().equals(msg.getRoutingData().getProfileId()) &&
                    conversationMemberEntities.size() > 1)
                return ResponseFactory.createResponse(msg, NOT_ACCEPTABLE, "First you need to appoint another admin");

            ConversationMemberEntity conversationMemberEntity = conversationMemberService.findByConversationIdAndProfileId
                    (msg.getData().getConversationId(),
                            msg.getRoutingData().getProfileId());
            conversationMemberEntity.setStatus(DELETED);
            conversationMemberEntity.setRemoveDate(System.currentTimeMillis());
            conversationMemberService.save(conversationMemberEntity);
//            if (conversationMemberEntities.size() == 1) {
//                conversationService.delete(msg.getData().getConversationId());
//                conversationSettingsService.delete(conversationSettingsEntity.getId());
//                messageService.delete(messageService.findByConversationId(msg.getData().getConversationId()));
//                messageStatusService.delete(messageStatusService.findByConversationId(msg.getData().getConversationId()));
//            }

        return ResponseFactory.createResponse(msg, new DeleteConversationResponse());
    }
}
