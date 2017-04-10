package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;
import com.alexkoveckiy.common.dao.repositories.ProfileStatusRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class ProfileStatusService extends BaseService<ProfileStatusEntity, ProfileStatusRepository> {
    public ProfileStatusEntity findByProfileId(String profileId) {
        return repository.findByProfileId(profileId);
    }
}
