package org.bootcamp.trashhunter.services;

import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractService<T> {

    @Autowired
    protected AbstractDAO<T> abstractDAO;

    public void add(T entity) {
        abstractDAO.add(entity);
    }

    public void update(T entity) {
        abstractDAO.update(entity);
    }

    public List<T> getAll() {
        return abstractDAO.getAll();
    }

    public List<T> getAllWithLimit(int limit) {
        return abstractDAO.getAllWithLimit(limit);
    }

    public T getByFieldNameAndValue(String name, Object object){
        return abstractDAO.getByFieldNameAndValue(name,object);
    }

    public T getById(Long id) {
        return abstractDAO.getById(id);
    }

    public void delete(T entity) {
        abstractDAO.delete(entity);
    }

    public void deleteById(Long id) {
        abstractDAO.deleteById(id);
    }

}
