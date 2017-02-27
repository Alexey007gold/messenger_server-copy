package com.softgroup.common.router.api;


import com.softgroup.common.datamapper.DataMapper;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.RequestData;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRequestHandler<T extends RequestData, R extends ResponseData> implements RequestHandler {

    @Autowired
    protected DataMapper dataMapper;

    @Autowired
    protected HandlerFactory<RequestHandler> handlerFactory;

    private static Class<?> clazz;

    @Override
    public Response<?> handle(Request<?> msg) {
        clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Request<T> request = new Request<>();
        request.setHeader(msg.getHeader());
        request.setData(dataMapper.convert(msg.getData(), clazz));

        return process(request);
    }
}
