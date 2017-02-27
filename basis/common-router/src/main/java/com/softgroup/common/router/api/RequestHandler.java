package com.softgroup.common.router.api;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;

public interface RequestHandler extends Handler {
    Response<?> process(Request<?> data);
}
