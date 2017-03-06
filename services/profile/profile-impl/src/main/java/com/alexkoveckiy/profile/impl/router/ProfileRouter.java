package com.alexkoveckiy.profile.impl.router;

import com.alexkoveckiy.common.router.api.AbstractRouterHandler;
import com.alexkoveckiy.common.router.api.CommonRouterHandler;
import com.alexkoveckiy.common.router.api.RequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ProfileRouter extends AbstractRouterHandler<RequestHandler> implements CommonRouterHandler {

    @Override
    public String getName() {
        return "profile";
    }
}
