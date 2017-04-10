package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;

/**
 * Created by alex on 23.03.17.
 */
public interface ProfileStatusRepository extends BaseRepository<ProfileStatusEntity> {
    ProfileStatusEntity findByProfileId(String profileId);
}
