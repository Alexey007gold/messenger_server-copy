package com.softgroup.common.router.api;

import com.softgroup.common.datamapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class HandlerFactory {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private List<RouterHandler> routerHandlers;

    private Map<String, Handler> handlerMap;

    @PostConstruct
    public void init() {
        handlerMap = new HashMap<>();
        for (Handler handler : routerHandlers) {
            handlerMap.put(handler.getName(), handler);
        }
    }

    public RouterHandler getRouterHandler(String name) {
        return (RouterHandler) handlerMap.get(name);
    }

//    public RequestHandler getRequestHandler(String name) {
//        return (RequestHandler) handlerMap.get(name);
//    }
}
