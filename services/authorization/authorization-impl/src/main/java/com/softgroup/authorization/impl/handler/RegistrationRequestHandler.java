package com.softgroup.authorization.impl.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softgroup.authorization.api.message.RegisterRequest;
import com.softgroup.authorization.api.message.RegisterResponse;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.router.api.AbstractRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class RegistrationRequestHandler extends AbstractRequestHandler<RegisterRequest, RegisterResponse> {
    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Response<RegisterResponse> handle(Request<?> msg) {
        msg = convertMessage(msg);
        return new Response<RegisterResponse>();
    }
}
