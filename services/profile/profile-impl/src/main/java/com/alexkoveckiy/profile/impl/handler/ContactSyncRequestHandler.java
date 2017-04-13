package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.entities.NumberEntity;
import com.alexkoveckiy.common.dao.service.ContactService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.dto.ContactDTO;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import com.alexkoveckiy.profile.api.message.ContactSyncRequest;
import com.alexkoveckiy.profile.api.message.ContactSyncResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class ContactSyncRequestHandler extends ProfileRequestHandler<ContactSyncRequest, ContactSyncResponse> {

    @Autowired
    private ContactService contactService;

    @Override
    public String getName() {
        return "contacts_sync";
    }

    @Override
    public Response<ContactSyncResponse> process(Request<ContactSyncRequest> msg) {
        addContacts(msg.getRoutingData().getProfileId(), msg.getData().getAddedContacts());
        removeContacts(msg.getRoutingData().getProfileId(), msg.getData().getRemovedContacts());

        return ResponseFactory.createResponse(msg, new ContactSyncResponse());
    }

    private void addContacts(String userId, List<ContactDTO> contacts) {
        if (contacts == null) return;

        Set<NumberEntity> existingNumberEntities;
        String contactId;
        ContactEntity contactEntity;

        for (ContactDTO contactDTO : contacts) {
            contactId = UUID.randomUUID().toString();
            contactEntity = contactService.findByProfileIdAndName(userId, contactDTO.getName());

            if (contactEntity != null) {
                existingNumberEntities = contactEntity.getNumbers();
                //remove existing numbers from new numbers set
                for (NumberEntity existingEntity : existingNumberEntities)
                    contactDTO.getNumbers().remove(existingEntity.getNumber());
                //create and add new numberEntities to existing
                for (String newNumber : contactDTO.getNumbers())
                    existingNumberEntities.add(new NumberEntity(contactId, newNumber));
                //save
                contactService.save(contactEntity);
            } else {
                Set<NumberEntity> newNumberEntities = new HashSet<>();
                for (String newNumber : contactDTO.getNumbers())
                    newNumberEntities.add(new NumberEntity(contactId, newNumber));

                contactService.save(new ContactEntity(userId, contactDTO.getName(), newNumberEntities));
            }
        }
    }

    private void removeContacts(String userId, List<ContactDTO> contacts) {
        if (contacts == null) return;

        NumberEntity entity;
        ContactEntity contactEntity;
        Iterator<NumberEntity> existingNumberEntitiesIterator;

        for (ContactDTO contactDTO : contacts) {
            //find contactEntity
            contactEntity = contactService.findByProfileIdAndName(userId, contactDTO.getName());
            if (contactEntity != null) {
                existingNumberEntitiesIterator = contactEntity.getNumbers().iterator();
                //remove needed
                while (existingNumberEntitiesIterator.hasNext()) {
                    entity = existingNumberEntitiesIterator.next();
                    if (contactDTO.getNumbers().contains(entity.getNumber()))
                        existingNumberEntitiesIterator.remove();
                }
                if (contactEntity.getNumbers().isEmpty())
                    contactService.delete(contactEntity.getId());
                else
                    contactService.save(contactEntity);
            }
        }
    }
}
