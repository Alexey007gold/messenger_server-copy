package com.alexkoveckiy.profile.api.handler;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.common.router.api.handler.AbstractRequestHandler;

public abstract class ProfileRequestHandler<T extends RequestData, R extends ResponseData> extends AbstractRequestHandler<T, R> {
}
