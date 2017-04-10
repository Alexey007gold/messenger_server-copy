package com.alexkoveckiy.frontend.ws;

import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;
import com.alexkoveckiy.common.dao.service.ProfileStatusService;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.api.handler.Handler;
import com.alexkoveckiy.wssession.WebSocketSessionService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by alex on 02.04.17.
 */

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private ProfileStatusService profileStatusService;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private Handler firstRouter;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Request<?> request = dataMapper.mapData(message.getPayload(), new TypeReference<Request<?>>() {});
        request.setRoutingData((RoutingData) session.getAttributes().get("routing_data"));
        session.sendMessage(new TextMessage(dataMapper.dataToString(firstRouter.handle(request))));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessionService.put(session, (RoutingData) session.getAttributes().get("routing_data"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String profileId = webSocketSessionService.getRoutingData(session).getProfileId();
        ProfileStatusEntity profileStatusEntity = profileStatusService.findByProfileId(profileId);
        profileStatusEntity.setLastTimeOnline(System.currentTimeMillis());
        profileStatusService.save(profileStatusEntity);
        webSocketSessionService.remove(session);
    }
}
