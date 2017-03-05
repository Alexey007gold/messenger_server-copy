package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.router.api.AbstractRequestHandler;
import com.alexkoveckiy.profile.api.message.ContactSyncRequest;
import com.alexkoveckiy.profile.api.message.ContactSyncResponse;
import com.alexkoveckiy.profile.api.router.ProfileRequestHandler;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class ContactSyncRequestHandler extends AbstractRequestHandler<ContactSyncRequest, ContactSyncResponse>
        implements ProfileRequestHandler<ContactSyncResponse> {


    @Override
    public String getName() {
        return "contacts_sync";
    }

    @Override
    public Response<ContactSyncResponse> process(Request<ContactSyncRequest> msg) {
        return null;
    }
}
