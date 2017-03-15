package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.TokenCreationTimeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by alex on 12.03.17.
 */
public interface TokenCreationTimeRepository extends PagingAndSortingRepository<TokenCreationTimeEntity, String> {
    List<TokenCreationTimeEntity> findByUserIdAndDeviceId(String userId, String deviceId);
}
