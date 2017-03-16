package com.alexkoveckiy.authorization.impl;

import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.common.router.api.HandlerFactoryByCommand;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationHandlerFactory extends HandlerFactoryByCommand<AuthorizationRequestHandler> {
}