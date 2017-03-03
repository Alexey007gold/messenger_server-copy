package com.alexkoveckiy.common.router.api;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseData;

public interface RequestHandler<R extends ResponseData> extends Handler {
    Response<R> process(Request<?> data);
}
