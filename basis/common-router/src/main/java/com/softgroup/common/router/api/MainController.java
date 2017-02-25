package com.softgroup.common.router.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softgroup.common.datamapper.DataMapper;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alex on 24.02.17.
 */

@RestController
@RequestMapping(path = "/",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MainController {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private HandlerFactory handlerFactory;

    @RequestMapping(path = "/")
    public Response<?> getRequest(@RequestBody final String request) {
        Request<?> msg = dataMapper.mapData(request, new TypeReference<Request<?>>() {});
        return handlerFactory.getRouterHandler(msg.getHeader().getType()).handle(msg);
    }
}
