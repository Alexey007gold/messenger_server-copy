package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.RegisterRequest;
import com.alexkoveckiy.authorization.api.message.RegisterResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.model.RegSession;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.dao.repositories.UserRepository;
import com.alexkoveckiy.common.protocol.ActionHeader;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseStatus;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
    public Response<RegisterResponse> process(Request<RegisterRequest> msg) {
        ActionHeader header;
        RegisterResponse data;
        ResponseStatus status;

        try {
            String phoneNumber = msg.getData().getPhoneNumber();
            if (numberIsOk(phoneNumber)) {
                RegSession regSession = regSessions.createRegSession(phoneNumber,
                        msg.getData().getDeviceID(),
                        msg.getData().getLocaleCode());

                header = new ActionHeader(UUID.randomUUID().toString(),
                        msg.getHeader().getUuid(),
                        "authorization",
                        "register",
                        "HTTP/1.1");

                data = new RegisterResponse(regSession.getUUID(),
                        regSession.getTimeOut(),
                        regSession.getAuthCode());
                status = new ResponseStatus(200, "OK");
                sendSms(phoneNumber, regSession.getAuthCode());
            } else
                throw new IllegalArgumentException();
        } catch (Exception e) {
            header = null;
            data = null;
            status = new ResponseStatus(400, "Bad request");
        }

        return new Response<>(header, data, status);
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
