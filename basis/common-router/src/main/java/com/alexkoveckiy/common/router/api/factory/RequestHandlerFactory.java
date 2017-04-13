package com.alexkoveckiy.common.router.api.factory;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 04.03.17.
 */

@Component
public class RequestHandlerFactory<T extends AbstractRequestHandler> extends HandlerFactory<T> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}
