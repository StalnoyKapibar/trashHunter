package org.bootcamp.trashhunter.services;

import org.bootcamp.trashhunter.dao.abstraction.AbstractDao;
import org.bootcamp.trashhunter.services.abstraction.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public abstract class AbstractServiceImpl<T> implements AbstractService<T> {

    @Autowired
    protected AbstractDao<T> abstractDAO;


    @Override
    public void add(T entity) {
        abstractDAO.add(entity);
    }


    @Override
    public void update(T entity) {
        abstractDAO.update(entity);
    }


    @Override
    public List<T> getAll() {
        return abstractDAO.getAll();
    }

    @Override
    public List<T> getAllWithLimit(int limit) {
        return abstractDAO.getAllWithLimit(limit);
    }

    @Override
    public T getByFieldNameAndValue(String name, Object object){
        return abstractDAO.getByFieldNameAndValue(name,object);
    }

    @Override
    public T getById(Long id) {
        return abstractDAO.getById(id);
    }

    @Override
    public void delete(T entity) {
        abstractDAO.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        abstractDAO.deleteById(id);
    }

}
