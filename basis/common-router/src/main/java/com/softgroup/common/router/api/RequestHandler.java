package com.softgroup.common.router.api;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseData;

public interface RequestHandler<R extends ResponseData> extends Handler {
    Response<R> process(Request<?> data);
}
