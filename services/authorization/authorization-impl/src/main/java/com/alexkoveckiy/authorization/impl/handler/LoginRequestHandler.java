package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.LoginRequest;
import com.alexkoveckiy.authorization.api.message.LoginResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class LoginRequestHandler extends AbstractRequestHandler<LoginRequest, LoginResponse> implements AuthorizationRequestHandler<LoginResponse> {

    @Autowired
    private RegSessions regSessions;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Response<LoginResponse> process(Request<?> msg) {
        return null;
    }
}
