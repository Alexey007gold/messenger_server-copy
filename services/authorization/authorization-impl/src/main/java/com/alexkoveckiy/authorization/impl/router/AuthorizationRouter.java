package com.alexkoveckiy.authorization.impl.router;

import com.alexkoveckiy.authorization.api.router.AuthorizationRouterHandler;
import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationRouter extends AbstractRouterHandler<AuthorizationRouterHandler> {

    @Override
    public String getName() {
        return "authorization";
    }
}
