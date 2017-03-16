package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;

public abstract class AbstractRouterHandler<T extends Handler> implements RouterHandler {

    @Override
	public Response<?> handle(Request<?> msg) {
		return getHandlerFactory().getHandler(msg).handle(msg);
	}

	protected abstract HandlerFactory<T> getHandlerFactory();
}
