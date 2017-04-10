package com.alexkoveckiy.authorization.api.handler;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;

public abstract class AuthorizationRequestHandler<T extends RequestData, R extends ResponseData> extends AbstractRequestHandler<T, R> {
}
