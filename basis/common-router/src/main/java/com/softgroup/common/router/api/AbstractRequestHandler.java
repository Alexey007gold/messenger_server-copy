package com.softgroup.common.router.api;


import com.fasterxml.jackson.core.type.TypeReference;
import com.softgroup.common.datamapper.DataMapper;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.RequestData;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractRequestHandler<T extends RequestData, R extends ResponseData> implements RequestHandler {

    @Autowired
    protected DataMapper dataMapper;

    @Autowired
    protected HandlerFactory handlerFactory;

    @Override
	public String getName() {
		return null;
	}

	@Override
	public Response<R> handle(Request<?> msg) {
        return null;
	}

	public Request<RequestData> convertMessage(Request<?> msg) {
        return dataMapper.convert(msg, new TypeReference<Request<RequestData>>() {});
    }

}
