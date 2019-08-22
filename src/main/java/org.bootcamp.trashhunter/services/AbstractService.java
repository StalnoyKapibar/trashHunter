package org.bootcamp.trashhunter.services;

import org.bootcamp.trashhunter.dao.impl.AbstractDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractService<T> implements org.bootcamp.trashhunter.services.abstraction.AbstractService<T> {

    @Autowired
    protected AbstractDAOImpl<T> abstractDAOImpl;

    @Override
    public void add(T entity) {
        abstractDAOImpl.add(entity);
    }

    @Override
    public void update(T entity) {
        abstractDAOImpl.update(entity);
    }

    @Override
    public List<T> getAll() {
        return abstractDAOImpl.getAll();
    }

    @Override
    public List<T> getAllWithLimit(int limit) {
        return abstractDAOImpl.getAllWithLimit(limit);
    }

    @Override
    public T getByFieldNameAndValue(String name, Object object){
        return abstractDAOImpl.getByFieldNameAndValue(name,object);
    }

    @Override
    public T getById(Long id) {
        return abstractDAOImpl.getById(id);
    }

    @Override
    public void delete(T entity) {
        abstractDAOImpl.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        abstractDAOImpl.deleteById(id);
    }

}
