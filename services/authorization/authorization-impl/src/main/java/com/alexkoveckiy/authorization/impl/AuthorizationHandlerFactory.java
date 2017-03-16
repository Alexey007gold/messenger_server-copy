package com.alexkoveckiy.authorization.impl;

import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationHandlerFactory extends HandlerFactory<AuthorizationRequestHandler> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}