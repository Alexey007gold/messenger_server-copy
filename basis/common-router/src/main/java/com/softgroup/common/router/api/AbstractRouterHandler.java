package com.softgroup.common.router.api;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractRouterHandler<T extends Handler> implements RouterHandler {

//	@Autowired
//    private TypeReferenceFactory typeReferenceFactory;

    @Autowired
    private T handler;

	@Override
	public String getRouteKey(Request<?> msg) {
		return msg.getHeader().getCommand();
	}

    @Override
	public Response<?> handle(Request<?> msg) {
		return handler.handle(msg);
	}
}
