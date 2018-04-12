package com.alexkoveckiy.messenger.impl.router;

import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import com.alexkoveckiy.messenger.impl.MessengerHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 30.03.17.
 */

@Component
public class MessengerRouter extends ByCommandRouterHandler<MessengerRequestHandler> {

    @Autowired
    private MessengerHandlerFactory handlerFactory;

    @Override
    public String getName() {
        return "messenger";
    }

    @Override
    protected HandlerFactory<MessengerRequestHandler> getHandlerFactory() {
        return handlerFactory;
    }
}
