package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;

public interface RouterHandler extends Handler {

	String getRouteKey(final Request<?> msg);
}
