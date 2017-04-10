package com.alexkoveckiy.messenger.api.handler;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;

/**
 * Created by alex on 30.03.17.
 */
public abstract class MessengerRequestHandler<T extends RequestData, R extends ResponseData> extends AbstractRequestHandler<T, R> {
}
