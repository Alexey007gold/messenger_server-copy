package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.repositories.ProfileSettingsRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class ProfileSettingsService extends BaseService<ProfileSettingsEntity, ProfileSettingsRepository> {
    public ProfileSettingsEntity findByProfileId(String profileId) {
        return repository.findByProfileId(profileId);
    }
}
