package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.EntityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by alex on 11.03.17.
 */
public abstract class BaseService<R extends PagingAndSortingRepository, E extends EntityInterface> {

    @Autowired
    protected R repository;

    public E save(E entity) {
        return (E) repository.save(entity);
    }

    public Iterable<E> save(Iterable<E> entities) {
        return repository.save(entities);
    }

    public void delete(E entity) {
        repository.delete(entity);
    }

    public void delete(Iterable<E> entities) {
        repository.delete(entities);
    }

    public Iterable<E> findAll() {
        return repository.findAll();
    }

    public E findOne(Serializable id) {
        return (E) repository.findOne(id);
    }
}
