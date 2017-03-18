package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.repositories.ContactRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class ContactService extends BaseService<ContactEntity, ContactRepository> {

    public List<ContactEntity> findByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public List<ContactEntity> findByUserAndNameAndNumber(String userId, String name, String number) {
        return repository.findByUserIdAndNameAndNumber(userId, name, number);
    }
}
