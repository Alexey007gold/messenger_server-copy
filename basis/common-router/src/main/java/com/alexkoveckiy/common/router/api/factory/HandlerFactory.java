package com.alexkoveckiy.common.router.api.factory;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 27.02.17.
 */
public abstract class HandlerFactory<T extends Handler> {

    private Map<String, T> handlerMap = new HashMap<>();

    public T getHandler(final Request<?> msg) {
        return handlerMap.get(getRouteKey(msg));
    }

    abstract protected String getRouteKey(final Request<?> msg);

    @Autowired
    public void setHandlerMap(List<T> handlers) {
        for (T handler : handlers) {
            handlerMap.put(handler.getName(), handler);
        }
    }
}
