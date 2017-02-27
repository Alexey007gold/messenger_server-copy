package com.softgroup.common.router.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class HandlerFactoryImpl<T extends Handler> implements HandlerFactory<T> {

    @Autowired
    private List<T> handlers;

    private Map<String, T> handlerMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (T handler : handlers) {
            handlerMap.put(handler.getName(), handler);
        }
    }

    public Handler getHandler(String name) {
        return handlerMap.get(name);
    }

}