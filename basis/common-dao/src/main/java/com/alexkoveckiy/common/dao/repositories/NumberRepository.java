package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.NumberEntity;

import java.util.List;

/**
 * Created by alex on 07.04.17.
 */
public interface NumberRepository extends BaseRepository<NumberEntity> {
    List<NumberEntity> findByNumber(String number);
}
