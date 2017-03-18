package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.EntityInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by alex on 11.03.17.
 */
public abstract class BaseService<E extends EntityInterface, R extends PagingAndSortingRepository<E, String>> {

    @Autowired
    protected R repository;

    public E save(E entity) {
        return repository.save(entity);
    }

    public Iterable<E> save(Iterable<E> entities) {
        return repository.save(entities);
    }

    public void delete(String id) {
        repository.delete(id);
    }
    public void delete(E entity) {
        repository.delete(entity);
    }

    public void delete(Iterable<E> entities) {
        repository.delete(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Iterable<E> findAll() {
        return repository.findAll();
    }

    public void findAll(Iterable<String> ids) {
        repository.findAll(ids);
    }

    public E findOne(String id) {
        return repository.findOne(id);
    }

    public void exists(String id) {
        repository.exists(id);
    }
}
