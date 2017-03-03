package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.message.SmsConfirmRequest;
import com.alexkoveckiy.authorization.api.message.SmsConfirmResponse;
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
public class SmsConfirmRequestHandler extends AbstractRequestHandler<SmsConfirmRequest, SmsConfirmResponse> implements AuthorizationRequestHandler<SmsConfirmResponse> {

    @Autowired
    private RegSessions regSessions;

    @Override
    public String getName() {
        return "sms_confirm";
    }

    @Override
    public Response<SmsConfirmResponse> process(Request<?> msg) {
        return null;
    }
}
