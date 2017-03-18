package com.alexkoveckiy.common.dao.repositories;


import com.alexkoveckiy.common.dao.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by alex on 05.03.17.
 */
public interface UserRepository extends BaseRepository<UserEntity> {
    UserEntity findByPhoneNumber(String phoneNumber);
}
