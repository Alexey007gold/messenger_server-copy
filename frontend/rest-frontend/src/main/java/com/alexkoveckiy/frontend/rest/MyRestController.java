package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.impl.FirstRouter;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * Created by alex on 24.02.17.
 */

@RestController
@RequestMapping(method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MyRestController {

    @Autowired
    private FirstRouter firstRouter;

    @Autowired
    private TokenHandler tokenHandler;

    @RequestMapping(path = "server")
    public Response<?> getRequest(@RequestHeader(name = "token", required = false) final String token,
                                  @RequestBody final Request<?> request) {
        try {
            if (!request.getHeader().getType().equals("authorization"))
                request.setRoutingData(getRoutingData(token));
            return firstRouter.handle(request);
        } catch (JoseException e) {
            return new Response<>(null, null, new ResponseStatus(403, "Forbidden"));
        } catch (Exception e) {
            return new Response<>(null, null, new ResponseStatus(400, "Bad request"));
        }
    }

    private RoutingData getRoutingData(String token) throws MalformedClaimException, JoseException, InvalidJwtException {
        return new RoutingData(tokenHandler.getPhoneNumberFromTemporaryToken(token));
    }
}