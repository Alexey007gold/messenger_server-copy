package com.alexkoveckiy.common.router.api;


import com.alexkoveckiy.common.datamapper.DataMapper;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRequestHandler<T extends RequestData, R extends ResponseData> implements RequestHandler<R> {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    protected HandlerFactory<RequestHandler> handlerFactory;

    private final Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public Response<?> handle(Request<?> msg) {
        Request<T> request = new Request<>();
        request.setHeader(msg.getHeader());
        request.setData(dataMapper.convert(msg.getData(), clazz));

        return process(request);
    }

    protected abstract Response<R> process(Request<T> msg);
}
