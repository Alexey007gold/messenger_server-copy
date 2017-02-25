package com.softgroup.authorization.impl.handler;

import com.softgroup.authorization.api.message.RegisterRequest;
import com.softgroup.authorization.api.message.RegisterResponse;
import com.softgroup.authorization.api.router.AuthorizationRequestHandler;
import com.softgroup.authorization.impl.model.CommonData;
import com.softgroup.authorization.impl.model.RegSession;
import com.softgroup.common.protocol.ActionHeader;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseStatus;
import com.softgroup.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by alex on 23.02.17.
 */

@Component
public class RegisterRequestHandler extends AbstractRequestHandler<RegisterRequest, RegisterResponse> implements AuthorizationRequestHandler {

    @Autowired
    private CommonData commonData;

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Response<RegisterResponse> process(Request<?> msg) {
        Request<RegisterRequest> request = (Request<RegisterRequest>) msg;
        ActionHeader header = null;
        RegisterResponse data = null;
        ResponseStatus status = null;

        try {
            String phoneNumber = request.getData().getPhoneNumber();
            if (numberIsOk(phoneNumber)) {
                RegSession regSession = commonData.createRegSession(phoneNumber,
                        request.getData().getDeviceID(),
                        request.getData().getLocaleCode());

                header = new ActionHeader(UUID.randomUUID().toString(),
                        request.getHeader().getUuid(),
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
