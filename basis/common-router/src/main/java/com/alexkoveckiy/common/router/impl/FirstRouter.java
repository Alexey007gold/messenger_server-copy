package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.CommonRouterHandler;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import com.alexkoveckiy.common.router.api.RouterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class FirstRouter implements RouterHandler {

    @Autowired
    private HandlerFactory handlerFactoryByType;

    @Override
    public String getName() {
        return "first_router";
    }

    @Override
    public Response<?> handle(Request<?> msg) {
        return handlerFactoryByType.getHandler(msg).handle(msg);
    }
}
