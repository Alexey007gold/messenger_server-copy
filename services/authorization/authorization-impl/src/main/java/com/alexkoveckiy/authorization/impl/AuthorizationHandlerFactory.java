package com.alexkoveckiy.authorization.impl;

import com.alexkoveckiy.authorization.api.handler.AuthorizationRequestHandler;
import com.alexkoveckiy.common.router.api.factory.RequestHandlerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class AuthorizationHandlerFactory extends RequestHandlerFactory<AuthorizationRequestHandler> {
}