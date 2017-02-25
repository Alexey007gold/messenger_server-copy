package com.softgroup.common.router.api;

import com.softgroup.common.protocol.Request;
import org.springframework.stereotype.Component;

@Component
public interface RouterHandler extends Handler {

	String getRouteKey(final Request<?> msg);
}
