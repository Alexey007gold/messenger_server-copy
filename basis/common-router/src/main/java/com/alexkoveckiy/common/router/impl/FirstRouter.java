package com.alexkoveckiy.common.router.impl;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.RouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 25.02.17.
 */

@Component
public class FirstRouter extends AbstractRouterHandler<RouterHandler> {

    @Override
    public String getName() {
        return "first_router";
    }
}
