package com.alexkoveckiy.common.dao.repositories;


import com.alexkoveckiy.common.dao.entities.ProfileEntity;

/**
 * Created by alex on 05.03.17.
 */
public interface ProfileRepository extends BaseRepository<ProfileEntity> {
    ProfileEntity findByPhoneNumber(String phoneNumber);
}
