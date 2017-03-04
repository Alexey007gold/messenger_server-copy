package com.alexkoveckiy.profile.impl.router;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.profile.api.router.ProfileRouterHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends AbstractRouterHandler<ProfileRouterHandler> {

    @Override
    public String getName() {
        return "profile";
    }
}
