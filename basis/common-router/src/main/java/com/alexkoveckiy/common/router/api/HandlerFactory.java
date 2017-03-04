package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;

/**
 * Created by alex on 27.02.17.
 */
public abstract class HandlerFactory<T extends Handler> {
    abstract T getHandler(final Request<?> msg);
    abstract String getRouteKey(final Request<?> msg);
}
