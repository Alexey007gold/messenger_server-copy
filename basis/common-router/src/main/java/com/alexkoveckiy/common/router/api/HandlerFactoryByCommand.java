package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class HandlerFactoryByCommand<T extends RequestHandler> extends HandlerFactory<T> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}