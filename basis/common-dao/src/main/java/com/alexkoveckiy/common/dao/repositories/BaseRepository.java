package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by alex on 16.03.17.
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends PagingAndSortingRepository<E, String> {
}
