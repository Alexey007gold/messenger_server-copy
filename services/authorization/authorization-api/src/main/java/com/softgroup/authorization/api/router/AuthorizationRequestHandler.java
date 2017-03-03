package com.softgroup.authorization.api.router;

import com.softgroup.common.protocol.ResponseData;
import com.softgroup.common.router.api.RequestHandler;
import org.springframework.stereotype.Component;

public interface AuthorizationRequestHandler<R extends ResponseData> extends RequestHandler<R> {
}
