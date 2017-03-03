package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.RegisterRequest;
import com.alexkoveckiy.authorization.api.message.RegisterResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class RegisterRequestHandler extends AbstractRequestHandler<RegisterRequest, RegisterResponse> implements AuthorizationRequestHandler<RegisterResponse> {

    @Autowired
    private RegSessions regSessions;

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Response<RegisterResponse> process(Request<?> msg) {
        return null;
    }

    private void sendSms(String phoneNumber, int authCode) {
    }

    private boolean numberIsOk(String phoneNumber) {
        return phoneNumber.charAt(0) == '+' &&
                !phoneNumber.substring(1).contains("+") &&
                phoneNumber.length() > 10 &&
                phoneNumber.length() < 15;
    }
}
