package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.ByTypeRouterHandler;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class FirstRouter extends AbstractRouterHandler<ByTypeRouterHandler> {

    @Autowired
    private HandlerFactoryByType handlerFactory;

    @Override
    public String getName() {
        return "first_router";
    }

    @Override
    protected HandlerFactory<ByTypeRouterHandler> getHandlerFactory() {
        return handlerFactory;
    }
}
