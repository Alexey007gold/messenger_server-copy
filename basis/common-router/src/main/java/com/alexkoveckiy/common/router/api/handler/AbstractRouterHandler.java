package com.alexkoveckiy.common.router.api.handler;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.factory.HandlerFactory;

public abstract class AbstractRouterHandler<T extends Handler> implements Handler {

    @Override
	public Response<?> handle(Request<?> msg) {
		return getHandlerFactory().getHandler(msg).handle(msg);
	}

	protected abstract HandlerFactory<T> getHandlerFactory();
}
