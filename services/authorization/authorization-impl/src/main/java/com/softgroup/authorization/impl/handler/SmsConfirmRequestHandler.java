package com.softgroup.authorization.impl.handler;

import com.softgroup.authorization.api.message.SmsConfirmRequest;
import com.softgroup.authorization.api.message.SmsConfirmResponse;
import com.softgroup.authorization.api.router.AuthorizationRequestHandler;
import com.softgroup.authorization.impl.model.CommonData;
import com.softgroup.authorization.impl.model.RegSession;
import com.softgroup.common.protocol.ActionHeader;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseStatus;
import com.softgroup.common.router.api.AbstractRequestHandler;
import com.softgroup.database.DataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class SmsConfirmRequestHandler extends AbstractRequestHandler<SmsConfirmRequest, SmsConfirmResponse> implements AuthorizationRequestHandler {
    @Autowired
    private CommonData commonData;

    @Autowired
    private DataBase dataBase;

    @Override
    public String getName() {
        return "sms_confirm";
    }

    @Override
    public Response<SmsConfirmResponse> process(Request<?> msg) {
        Request<SmsConfirmRequest> request = (Request<SmsConfirmRequest>) msg;
        ActionHeader header = null;
        SmsConfirmResponse data = null;
        ResponseStatus status = null;

        try {
            String uuid = request.getData().getRegistrationRequestUuid();
            int authCode = request.getData().getAuthCode();

            RegSession regSession = commonData.takeRegSession(uuid);
            if (regSession != null && !regSession.hasExpired() && regSession.getAuthCode() == authCode) {
                dataBase.users.addUser(regSession.getPhoneNumber());
                String deviceToken = regSession.newDeviceToken();

                header = new ActionHeader(UUID.randomUUID().toString(),
                        request.getHeader().getUuid(),
                        "authorization",
                        "sms_confirm",
                        "HTTP/1.1");
                data = new SmsConfirmResponse(deviceToken);
                status = new ResponseStatus(200, "OK");
                status.setMessage("OK");
            } else {
                status = new ResponseStatus(403, "Forbidden");
            }
        } catch (Exception e) {
            status = new ResponseStatus(400, "Bad request");
        }

        return new Response<>(header, data, status);
    }
}
