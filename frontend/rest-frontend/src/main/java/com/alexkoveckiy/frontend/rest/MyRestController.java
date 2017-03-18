package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.impl.FirstRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * Created by alex on 24.02.17.
 */

@RestController
@RequestMapping(method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        path = "server")
public class MyRestController {

    @Autowired
    private FirstRouter firstRouter;

    @RequestMapping(path = "public")
    public Response<?> processPublicRequest(@RequestBody final Request<?> request) {
        try {
            return firstRouter.handle(request);
        } catch (Exception e) {
            return new Response<>(null, null, new ResponseStatus(400, "Bad request"));
        }
    }

    @RequestMapping(path = "private")
    public Response<?> processPrivateRequest(@RequestBody final Request<?> request, HttpSession session) {
        try {
            request.setRoutingData((RoutingData) session.getAttribute("routing_data"));
            return firstRouter.handle(request);
        } catch (Exception e) {
            return new Response<>(null, null, new ResponseStatus(400, "Bad request"));
        }
    }
}