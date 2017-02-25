package com.softgroup.common.router.api;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import org.springframework.stereotype.Component;

@Component
public interface Handler {
    String getName();

    Response<?> handle(final Request<?> msg);
}
