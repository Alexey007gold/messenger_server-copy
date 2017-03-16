package com.alexkoveckiy.profile.impl;

import com.alexkoveckiy.common.router.api.HandlerFactoryByCommand;
import com.alexkoveckiy.profile.api.router.ProfileRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class ProfileHandlerFactory extends HandlerFactoryByCommand<ProfileRequestHandler> {
}