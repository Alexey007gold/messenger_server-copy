package com.alexkoveckiy.messenger.impl.handler;

import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.wssession.WebSocketSessionService;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.api.message.IsTypingInChatRequest;
import com.alexkoveckiy.messenger.api.message.IsTypingInChatResponse;
import com.alexkoveckiy.messenger.api.notification.IsTypingInChatNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.util.UUID;

/**
 * Created by alex on 03.04.17.
 */
@Component
public class IsTypingInChatRequestHandler extends MessengerRequestHandler<IsTypingInChatRequest, IsTypingInChatResponse> {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private DataMapper dataMapper;

    @Override
    public String getName() {
        return "is_typing_in_chat";
    }

    @Override
    protected Response<IsTypingInChatResponse> process(Request<IsTypingInChatRequest> msg) {
        sendNotification(msg);
        return ResponseFactory.createResponse(msg, new IsTypingInChatResponse());
    }

    private void sendNotification(Request<IsTypingInChatRequest> msg) {
        ActionHeader actionHeader = new ActionHeader(UUID.randomUUID().toString(), msg.getHeader().getUuid(),
                "is_typing_in_chat_notification", "profile", "HTTP/1.1");
        IsTypingInChatNotification isTypingInChatNotification = new IsTypingInChatNotification(msg.getData().getConversationId(),
                msg.getRoutingData().getProfileId());
        Response<IsTypingInChatNotification> notification = ResponseFactory.createResponse(actionHeader, isTypingInChatNotification);
        TextMessage message = new TextMessage(dataMapper.dataToString(notification));
        for (ConversationMemberEntity conversationMember : conversationMemberService.findByConversationId(msg.getData().getConversationId())) {
            try {
                if (!conversationMember.getProfileId().equals(msg.getRoutingData().getProfileId()))
                    webSocketSessionService.getSession(conversationMember.getProfileId()).sendMessage(message);
            } catch (Exception ignored) {}
        }
    }
}
