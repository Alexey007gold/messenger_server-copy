package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 04.03.17.
 */

@Component
public class HandlerFactoryByType<T extends RouterHandler> extends HandlerFactory<T> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getType();
    }
}
