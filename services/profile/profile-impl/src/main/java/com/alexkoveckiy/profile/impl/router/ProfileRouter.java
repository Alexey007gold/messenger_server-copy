package com.alexkoveckiy.profile.impl.router;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.router.api.AbstractByCommandRouterHandler;
import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.profile.api.router.ProfileRouterHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends AbstractByCommandRouterHandler<ProfileRouterHandler> {

    @Override
    public String getName() {
        return "profile";
    }
}
