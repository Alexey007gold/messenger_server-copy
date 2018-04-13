package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.MessageEntity;
import com.alexkoveckiy.common.dao.entities.MessageStatusEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.MessageService;
import com.alexkoveckiy.common.dao.service.MessageStatusService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.wssession.WebSocketSessionService;
import com.alexkoveckiy.messenger.api.dto.MessageDTO;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.api.message.SendMessageRequest;
import com.alexkoveckiy.messenger.api.message.SendMessageResponse;
import com.alexkoveckiy.messenger.api.notification.NewMessageNotification;
import com.alexkoveckiy.messenger.impl.service.ConversationMembershipCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.alexkoveckiy.common.dao.entities.MessageStatusEntity.MessageStatus.SERVER_RECEIVED;

/**
 * Created by alex on 31.03.17.
 */
@Component
public class SendMessageRequestHandler extends MessengerRequestHandler<SendMessageRequest, SendMessageResponse> {

    @Autowired
    private ConversationMembershipCheckService conversationMembershipCheckService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageStatusService messageStatusService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Autowired
    private DataMapper dataMapper;

    @Override
    public String getName() {
        return "send_message";
    }

    @Override
    protected Response<SendMessageResponse> process(Request<SendMessageRequest> msg) throws IllegalAccessException {
        conversationMembershipCheckService.checkIsActiveMember(msg.getData().getMessage().getConversationId(), msg.getRoutingData().getProfileId());

        MessageEntity messageEntity = messageService.save(new MessageEntity(
                msg.getRoutingData().getProfileId(),
                msg.getData().getMessage().getConversationId(),
                msg.getData().getMessage().getPayload(),
                msg.getData().getMessage().getMessageType(),
                msg.getData().getMessage().getCreationTime(),
                System.currentTimeMillis()));

        for (ConversationMemberEntity conversationMember : conversationMemberService.findByConversationId(msg.getData().getMessage().getConversationId())) {
            if (!conversationMember.getProfileId().equals(msg.getRoutingData().getProfileId())) {
                messageStatusService.save(new MessageStatusEntity(messageEntity.getId(),
                        conversationMember.getProfileId(),
                        msg.getData().getMessage().getConversationId(),
                        SERVER_RECEIVED));
            }
        }

        sendNotification(msg, messageEntity);

        SendMessageResponse data = new SendMessageResponse(modelMapperService.map(messageEntity, MessageDTO.class));

        return ResponseFactory.createResponse(msg, data);
    }

    private void sendNotification(Request<SendMessageRequest> msg, MessageEntity messageEntity) {
        CompletableFuture.supplyAsync(() -> {
            MessageDTO messageDTO = modelMapperService.map(messageEntity, MessageDTO.class);
            ActionHeader header = new ActionHeader(UUID.randomUUID().toString(), msg.getHeader().getUuid(),
                    "new_message_notification", "profile", "HTTP/1.1");
            NewMessageNotification newMessageNotification = new NewMessageNotification(messageDTO);
            Response<NewMessageNotification> notification = ResponseFactory.createResponse(header, newMessageNotification);
            TextMessage message = new TextMessage(dataMapper.dataToString(notification));

            conversationMemberService.findByConversationId(msg.getData().getMessage().getConversationId())
                    .parallelStream()
                    .filter(m -> !m.getProfileId().equals(msg.getRoutingData().getProfileId()))
                    .forEach(m -> {
                        try {
                            webSocketSessionService.getSession(m.getProfileId()).sendMessage(message);
                        } catch (IOException | NullPointerException ignored) {}
                    });

            return null;
        });
    }


}
