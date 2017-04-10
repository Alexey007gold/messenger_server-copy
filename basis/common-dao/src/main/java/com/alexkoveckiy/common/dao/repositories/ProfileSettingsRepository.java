package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;

/**
 * Created by alex on 23.03.17.
 */
public interface ProfileSettingsRepository extends BaseRepository<ProfileSettingsEntity> {
    ProfileSettingsEntity findByProfileId(String profileId);
}
