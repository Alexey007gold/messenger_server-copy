package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;

public interface Handler {
    String getName();

    Response<?> handle(final Request<?> msg);
}
