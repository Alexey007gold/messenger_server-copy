package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ContactEntity;

import java.util.List;

/**
 * Created by alex on 05.03.17.
 */
public interface ContactRepository extends BaseRepository<ContactEntity> {
    List<ContactEntity> findByProfileId(String profileId);
    ContactEntity findByProfileIdAndName(String profileId, String name);
}
