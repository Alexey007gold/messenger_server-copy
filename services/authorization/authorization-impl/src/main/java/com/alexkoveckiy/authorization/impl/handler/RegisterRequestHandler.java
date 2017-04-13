package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.handler.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.api.message.RegisterRequest;
import com.alexkoveckiy.authorization.api.message.RegisterResponse;
import com.alexkoveckiy.authorization.impl.model.RegSession;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.NOT_ACCEPTABLE;
import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.TOO_MANY_REQUESTS;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class RegisterRequestHandler extends AuthorizationRequestHandler<RegisterRequest, RegisterResponse> {

    @Autowired
    private RegSessions regSessions;

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Response<RegisterResponse> process(Request<RegisterRequest> msg) {
        String phoneNumber = msg.getData().getPhoneNumber();
        if (!numberIsOk(phoneNumber))
            return ResponseFactory.createResponse(msg, NOT_ACCEPTABLE);
        if (!regSessions.exists(phoneNumber, msg.getData().getDeviceId())) {
            RegSession regSession = regSessions.createRegSession(phoneNumber,
                    msg.getData().getDeviceId(), msg.getData().getLocaleCode());

            sendSms(phoneNumber, regSession.getAuthCode());

            return ResponseFactory.createResponse(msg, new RegisterResponse(regSession.getUUID(),
                    regSession.getTimeOut(), regSession.getAuthCode()));
        }
        return ResponseFactory.createResponse(msg, TOO_MANY_REQUESTS);
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
