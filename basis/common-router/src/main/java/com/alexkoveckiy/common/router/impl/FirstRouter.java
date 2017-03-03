package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.CommonRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class FirstRouter extends AbstractRouterHandler<CommonRouterHandler> {

    @Override
    public String getName() {
        return "first_router";
    }

    @Override
    public String getRouteKey(Request<?> msg) {
        return msg.getHeader().getType();
    }
}
