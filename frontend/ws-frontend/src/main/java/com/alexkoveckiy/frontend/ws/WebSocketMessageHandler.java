package com.alexkoveckiy.frontend.ws;

import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.isonline.IsOnlineService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.router.api.handler.Handler;
import com.alexkoveckiy.common.token.api.TokenHandler;
import com.alexkoveckiy.common.wssession.WebSocketSessionService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.FORBIDDEN;

/**
 * Created by alex on 02.04.17.
 */

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private IsOnlineService isOnlineService;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private Handler firstRouter;

    @Override
    @SuppressWarnings("unchecked")
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Request<?> request = dataMapper.mapData(message.getPayload(), new TypeReference<Request<?>>() {});
        Response<?> response;
        if (!request.getHeader().getType().equals("authorization")) {
            request.setRoutingData(webSocketSessionService.getRoutingData(session));
            if (request.getRoutingData() != null) {
                response = firstRouter.handle(request);
            } else {
                response = ResponseFactory.createResponse(request, FORBIDDEN);
            }
        } else {
            response = firstRouter.handle(request);
            if (request.getHeader().getCommand().equals("login")) {
                String token = ((Response<LoginResponse>)response).getData().getToken();
                webSocketSessionService.put(session, tokenHandler.getRoutingDataFromTemporaryToken(token));
            }
        }
        session.sendMessage(new TextMessage(dataMapper.dataToString(response)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        isOnlineService.checkOnline(webSocketSessionService.getRoutingData(session).getProfileId());
        webSocketSessionService.remove(session);
    }
}
