package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.LoginRequest;
import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class LoginRequestHandler extends AbstractRequestHandler<LoginRequest, LoginResponse> implements AuthorizationRequestHandler<LoginResponse> {

    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Response<LoginResponse> process(Request<LoginRequest> msg) {
        ActionHeader header;
        LoginResponse data;
        ResponseStatus status;

        try {
            String deviceToken = msg.getData().getDeviceToken();
            String phoneNumber = tokenHandler.getPhoneNumberFromDeviceToken(deviceToken);

            header = new ActionHeader(UUID.randomUUID().toString(),
                    msg.getHeader().getUuid(),
                    "authorization",
                    "login",
                    "HTTP/1.1");
            data = new LoginResponse(tokenHandler.createTemporaryToken(phoneNumber));
            status = new ResponseStatus(200, "OK");
        } catch (JoseException | InvalidJwtException | MalformedClaimException e) {
            header = null;
            data = null;
            status = new ResponseStatus(403, "Forbidden");
        }
        return new Response<>(header, data, status);
    }
}
