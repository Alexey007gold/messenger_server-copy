package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.SmsConfirmRequest;
import com.alexkoveckiy.authorization.api.message.SmsConfirmResponse;
import com.alexkoveckiy.authorization.api.router.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.impl.model.CommonData;
import com.alexkoveckiy.authorization.impl.model.RegSession;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.dao.entities.UserEntity;
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
 * Created by alex on 24.02.17.
 */

@Component
public class SmsConfirmRequestHandler extends AbstractRequestHandler<SmsConfirmRequest, SmsConfirmResponse> implements AuthorizationRequestHandler<SmsConfirmResponse> {

    @Autowired
    private RegSessions regSessions;

    @Autowired
    private CommonData commonData;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getName() {
        return "sms_confirm";
    }

    @Override
    public Response<SmsConfirmResponse> process(Request<SmsConfirmRequest> msg) {
        ActionHeader header = null;
        SmsConfirmResponse data = null;
        ResponseStatus status = null;

        try {
            String uuid = msg.getData().getRegistrationRequestUuid();
            int authCode = msg.getData().getAuthCode();

            RegSession regSession = regSessions.takeRegSession(uuid);
            if (regSession != null && !regSession.hasExpired() && regSession.getAuthCode() == authCode) {
                userRepository.save(new UserEntity(regSession.getPhoneNumber()));
                String deviceToken = regSession.newDeviceToken(commonData);

                header = new ActionHeader(UUID.randomUUID().toString(),
                        msg.getHeader().getUuid(),
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
