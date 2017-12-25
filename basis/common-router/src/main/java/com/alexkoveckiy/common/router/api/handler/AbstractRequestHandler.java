package com.alexkoveckiy.common.router.api.handler;

import com.alexkoveckiy.common.protocol.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.INTERNAL_SERVER_ERROR;

public abstract class AbstractRequestHandler<T extends RequestData, R extends ResponseData> implements Handler {

    @Autowired
    private MessageFactory messageFactory;

    private final Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public Response<?> handle(Request<?> msg) {
        Request<T> request = messageFactory.getRequestWithConcreteData(msg, clazz);

        try {
            return process(request);
        } catch (Exception e) {
            return ResponseFactory.createResponse(request, INTERNAL_SERVER_ERROR);
        }
    }

    protected abstract Response<R> process(Request<T> msg) throws Exception;
}
