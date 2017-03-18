package com.alexkoveckiy.profile.impl.router;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.ByTypeRouterHandler;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import com.alexkoveckiy.profile.api.router.ProfileRequestHandler;
import com.alexkoveckiy.profile.impl.ProfileHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends AbstractRouterHandler<ProfileRequestHandler> implements ByTypeRouterHandler {

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
