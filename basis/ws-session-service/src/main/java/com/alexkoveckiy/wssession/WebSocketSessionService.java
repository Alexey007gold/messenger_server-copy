package com.alexkoveckiy.wssession;

import com.alexkoveckiy.common.protocol.RoutingData;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 02.04.17.
 */
@Component
public class WebSocketSessionService {
    private Map<WebSocketSession, RoutingData> socketSessionRoutingDataMap = new HashMap<>();
    private Map<String, WebSocketSession> profileIdWebSocketSessionMap = new HashMap<>();

    public void put(WebSocketSession session, RoutingData routingData) {
        socketSessionRoutingDataMap.put(session, routingData);
        profileIdWebSocketSessionMap.put(routingData.getProfileId(), session);
    }

    public RoutingData getRoutingData(WebSocketSession session) {
        return socketSessionRoutingDataMap.get(session);
    }

    public WebSocketSession getSession(String profileId) {
        return profileIdWebSocketSessionMap.get(profileId);
    }

    public void remove(WebSocketSession session) {
        profileIdWebSocketSessionMap.remove(socketSessionRoutingDataMap.remove(session).getProfileId());
    }
}
