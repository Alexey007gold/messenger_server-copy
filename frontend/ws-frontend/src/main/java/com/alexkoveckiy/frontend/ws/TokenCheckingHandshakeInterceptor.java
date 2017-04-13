package com.alexkoveckiy.frontend.ws;

import com.alexkoveckiy.common.token.api.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by alex on 02.04.17.
 */
@Component
public class TokenCheckingHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        String token = request.getHeaders().get("x-token").get(0);
//        RoutingData routingData = tokenHandler.getRoutingDataFromTemporaryToken(token);
//        attributes.put("routing_data", routingData);
        return true;
    }
}
