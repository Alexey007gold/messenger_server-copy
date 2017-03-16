package com.alexkoveckiy.authorization.impl.router;

import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.AuthorizationHandlerFactory;
import com.alexkoveckiy.common.router.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationRouter extends AbstractRouterHandler<AuthorizationRequestHandler> implements ByTypeRouterHandler {

    @Autowired
    private AuthorizationHandlerFactory handlerFactory;

    @Override
    public String getName() {
        return "authorization";
    }

    @Override
    protected HandlerFactory<AuthorizationRequestHandler> getHandlerFactory() {
        return handlerFactory;
    }
}
