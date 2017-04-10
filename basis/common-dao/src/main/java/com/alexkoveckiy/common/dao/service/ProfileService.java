package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.repositories.ProfileRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class ProfileService extends BaseService<ProfileEntity, ProfileRepository> {
    public ProfileEntity findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }
}
