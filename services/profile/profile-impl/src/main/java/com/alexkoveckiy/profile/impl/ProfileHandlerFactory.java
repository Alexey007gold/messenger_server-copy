package com.alexkoveckiy.profile.impl;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.HandlerFactory;
import com.alexkoveckiy.profile.api.router.ProfileRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class ProfileHandlerFactory extends HandlerFactory<ProfileRequestHandler> {

    @Override
    protected String getRouteKey(final Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}