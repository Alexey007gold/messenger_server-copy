package com.softgroup.profile.impl.handler;

import com.softgroup.database.DataBase;
import com.softgroup.profile.api.message.ContactSyncRequest;
import com.softgroup.profile.api.message.ContactSyncResponse;
import com.softgroup.profile.api.model.Contact;
import com.softgroup.profile.api.router.ProfileRequestHandler;
import com.softgroup.common.protocol.ActionHeader;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseStatus;
import com.softgroup.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class ContactSyncRequestHandler extends AbstractRequestHandler<ContactSyncRequest, ContactSyncResponse>
        implements ProfileRequestHandler {

    @Autowired
    private DataBase dataBase;

    @Override
    public String getName() {
        return "contacts_sync";
    }

    @Override
    public Response<ContactSyncResponse> process(Request<?> msg) {
        Request<ContactSyncRequest> request = (Request<ContactSyncRequest>) msg;
        ActionHeader header = null;
        ContactSyncResponse data = null;
        ResponseStatus status = null;

        try {
            String token = request.getData().getToken();
            if (!isTokenExpired(token)) {
                String phoneNumber = getPhoneNumberFromDeviceToken(token);
                if (dataBase.users.hasUser(phoneNumber)) {
                    ArrayList<Contact> addedContacts = request.getData().getAddedContacts();
                    dataBase.contacts.addContacts(addedContacts);
                    ArrayList<Contact> removedContacts = request.getData().getRemovedContacts();
                    dataBase.contacts.removeContacts(removedContacts);

                    header = new ActionHeader(UUID.randomUUID().toString(),
                            request.getHeader().getUuid(),
                            "contact_sync",
                            "profile",
                            "HTTP/1.1");
                    data = null;//new ContactSyncResponse();
                    status = new ResponseStatus(200, "OK");
                } else {
                }
            } else {
                status = new ResponseStatus(404, "Not Found");
            }
        } catch (Exception e) {
            header = null;
            data = null;
            status = new ResponseStatus(400, "Bad Request");
        }
        return new Response<>(header, data, status);
    }
}
