package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.TokenEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by alex on 12.03.17.
 */
public interface TokenRepository extends PagingAndSortingRepository<TokenEntity, String> {
}
