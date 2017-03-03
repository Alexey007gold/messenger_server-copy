package com.alexkoveckiy.authorization.api.router;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.common.router.api.RequestHandler;

public interface AuthorizationRequestHandler<R extends ResponseData> extends RequestHandler<R> {
}
