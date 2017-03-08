package com.alexkoveckiy.frontend.rest;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.protocol.RoutingData;
import com.alexkoveckiy.common.router.impl.FirstRouter;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwt.JwtClaims;
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
    private CommonData commonData;

    @RequestMapping(path = "server")
    public Response<?> getRequest(@RequestHeader(name = "token", required = false) final String token,
                                  @RequestBody final Request<?> request) {
        try {
            request.setRoutingData(getRoutingData(token));
            return firstRouter.handle(request);
        } catch (Exception e) {
            return new Response<>(null, null, new ResponseStatus(400, "Bad request"));
        }
    }

    private RoutingData getRoutingData(String token) {
        try {
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setKey(commonData.aesKey);
            jwe.setCompactSerialization(token);
            JwtClaims claims = JwtClaims.parse(jwe.getPayload());
            return new RoutingData(claims.getStringClaimValue("phone_number"));
        } catch (MalformedClaimException | InvalidJwtException | JoseException e) {
            return null;
        }
    }
}