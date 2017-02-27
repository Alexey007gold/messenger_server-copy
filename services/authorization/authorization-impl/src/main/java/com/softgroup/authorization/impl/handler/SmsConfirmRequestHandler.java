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
        return null;
    }
}
