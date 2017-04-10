package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.BaseEntity;
import com.alexkoveckiy.common.dao.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alex on 11.03.17.
 */
public abstract class BaseService<E extends BaseEntity, R extends BaseRepository<E>> {

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

    public Iterable<E> findAll(Iterable<String> ids) {
        return repository.findAll(ids);
    }

    public E findOne(String id) {
        return repository.findOne(id);
    }

    public void exists(String id) {
        repository.exists(id);
    }
}
