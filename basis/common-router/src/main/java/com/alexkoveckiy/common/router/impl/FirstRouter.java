package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.router.api.factory.ByCommandHandlerFactory;
import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import com.alexkoveckiy.common.router.api.handler.ByTypeRouterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class FirstRouter extends ByTypeRouterHandler {

    @Autowired
    private ByCommandHandlerFactory handlerFactory;

    @Override
    public String getName() {
        return "first_router";
    }

    @Override
    protected HandlerFactory<ByCommandRouterHandler> getHandlerFactory() {
        return handlerFactory;
    }
}
