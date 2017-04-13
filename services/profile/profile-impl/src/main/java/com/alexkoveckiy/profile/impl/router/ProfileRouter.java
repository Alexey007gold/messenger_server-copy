package com.alexkoveckiy.profile.impl.router;

import com.alexkoveckiy.common.router.api.factory.HandlerFactory;
import com.alexkoveckiy.common.router.api.handler.ByCommandRouterHandler;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import com.alexkoveckiy.profile.impl.ProfileHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends ByCommandRouterHandler<ProfileRequestHandler> {

    @Autowired
    private ProfileHandlerFactory handlerFactory;

    @Override
    public String getName() {
        return "profile";
    }

    @Override
    protected HandlerFactory<ProfileRequestHandler> getHandlerFactory() {
        return handlerFactory;
    }
}
