package com.alexkoveckiy.messenger.impl;

import com.alexkoveckiy.common.router.api.factory.ByCommandHandlerFactory;
import com.alexkoveckiy.common.router.api.factory.RequestHandlerFactory;
import com.alexkoveckiy.messenger.api.handler.MessengerRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class MessengerHandlerFactory extends RequestHandlerFactory<MessengerRequestHandler> {
}