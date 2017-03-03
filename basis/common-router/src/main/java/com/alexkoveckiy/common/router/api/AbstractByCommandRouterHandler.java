package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;

/**
 * Created by alex on 03.03.17.
 */
public abstract class AbstractByCommandRouterHandler<T extends Handler> extends AbstractRouterHandler<T> {

    @Override
    public String getRouteKey(Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}
