package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.NumberEntity;
import com.alexkoveckiy.common.dao.repositories.NumberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by alex on 07.04.17.
 */
@Component
public class NumberService extends BaseService<NumberEntity, NumberRepository> {
    public List<NumberEntity> findByNumber(String number) {
        return repository.findByNumber(number);
    }
}
