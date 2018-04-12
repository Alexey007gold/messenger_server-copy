package com.alexkoveckiy.common.router.api.factory;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 16.03.17.
 */
@Component
public class ByCommandHandlerFactory extends HandlerFactory<ByCommandRouterHandler> {

    @Override
    protected String getRouteKey(Request<?> msg) {
        return msg.getHeader().getType();
    }
}
