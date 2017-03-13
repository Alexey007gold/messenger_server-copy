package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.TokenEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by alex on 12.03.17.
 */
public interface TokenRepository extends PagingAndSortingRepository<TokenEntity, String> {
    List<TokenEntity> findByPhoneNumberAndDeviceId(String phoneNumber, String deviceId);
}
