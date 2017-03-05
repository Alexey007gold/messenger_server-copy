package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by alex on 05.03.17.
 */
public interface ContactRepository extends PagingAndSortingRepository<ContactEntity, Long> {
    List<ContactEntity> findByUser(String user);
    List<ContactEntity> findByUserAndNameAndNumber(String user, String name, String number);
}
