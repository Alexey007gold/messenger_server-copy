package com.softgroup.profile.impl.handler;

import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.router.api.AbstractRequestHandler;
import com.softgroup.profile.api.message.ContactSyncRequest;
import com.softgroup.profile.api.message.ContactSyncResponse;
import com.softgroup.profile.api.router.ProfileRequestHandler;
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
    public Response<ContactSyncResponse> process(Request<?> msg) {
        return null;
    }
}
