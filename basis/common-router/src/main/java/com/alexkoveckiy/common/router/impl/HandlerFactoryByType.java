package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.ByTypeRouterHandler;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 04.03.17.
 */

@Component
public class HandlerFactoryByType extends HandlerFactory<ByTypeRouterHandler> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getType();
    }
}
