package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.router.api.Handler;
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
@RequestMapping(method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MyRestController {

    @Autowired
    private Handler firstRouter;

    @RequestMapping(path = "server")
    public Response<?> getRequest(@RequestBody final Request<?> request) {
        try {
            return firstRouter.handle(request);
        } catch (Exception e) {
            return new Response<>(null, null, new ResponseStatus(400, "Bad request"));
        }
    }
}