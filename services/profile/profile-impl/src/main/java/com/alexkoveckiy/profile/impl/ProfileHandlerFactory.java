package com.alexkoveckiy.profile.impl;

import com.alexkoveckiy.common.router.api.factory.RequestHandlerFactory;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class ProfileHandlerFactory extends RequestHandlerFactory<ProfileRequestHandler> {
}