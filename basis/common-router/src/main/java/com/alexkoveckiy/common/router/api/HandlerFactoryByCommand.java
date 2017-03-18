package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;

/**
 * Created by alex on 16.03.17.
 */
public abstract class HandlerFactoryByCommand<T extends Handler> extends HandlerFactory<T> {

    @Override
    protected String getRouteKey(Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}
