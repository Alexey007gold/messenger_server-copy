package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.entities.NumberEntity;
import com.alexkoveckiy.common.dao.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class ContactService extends BaseService<ContactEntity, ContactRepository> {

    @Autowired
    private NumberService numberService;

    public List<ContactEntity> findByProfileId(String profileId) {
        return repository.findByProfileId(profileId);
    }

    public ContactEntity findByProfileIdAndName(String profileId, String name) {
        return repository.findByProfileIdAndName(profileId, name);
    }

    public List<ContactEntity> findContactByProfileIdAndNumber(String profileId, String phoneNumber) {
        List<ContactEntity> contactEntities = new ArrayList<>();
        ContactEntity contactEntity;
        for (NumberEntity numberEntity : numberService.findByNumber(phoneNumber)) {
            contactEntity = findOne(numberEntity.getContactId());
            if (contactEntity != null && contactEntity.getProfileId().equals(profileId))
                contactEntities.add(contactEntity);
        }
        return contactEntities;
    }
}
