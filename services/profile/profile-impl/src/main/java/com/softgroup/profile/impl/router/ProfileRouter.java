package com.softgroup.profile.impl.router;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.router.api.AbstractRouterHandler;
import com.softgroup.profile.api.router.ProfileRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends AbstractRouterHandler<ProfileRouterHandler> {

    public String getName() {
        return "profile";
    }

    @Override
    public String getRouteKey(Request<?> msg) {
        return msg.getHeader().getCommand();
    }
}
