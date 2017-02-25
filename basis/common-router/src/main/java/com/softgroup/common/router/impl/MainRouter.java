package com.softgroup.common.router.impl;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.router.api.AbstractRouterHandler;
import com.softgroup.common.router.api.CommonRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class MainRouter extends AbstractRouterHandler<CommonRouterHandler> {

    @Override
    public String getName() {
        return "main";
    }

    @Override
    public String getRouteKey(Request<?> msg) {
        return msg.getHeader().getType();
    }
}
