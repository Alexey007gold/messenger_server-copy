package com.alexkoveckiy.authorization.impl.router;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.CommonRouterHandler;
import com.alexkoveckiy.common.router.api.RequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationRouter extends AbstractRouterHandler<RequestHandler> implements CommonRouterHandler {

    @Override
    public String getName() {
        return "authorization";
    }
}
