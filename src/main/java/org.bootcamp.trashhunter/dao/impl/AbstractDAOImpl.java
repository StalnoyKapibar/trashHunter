package org.bootcamp.trashhunter.dao.impl;

import org.bootcamp.trashhunter.dao.impl.abstraction.AbstractDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository("abstractDAO")
public abstract class AbstractDAOImpl<T> implements AbstractDao<T> {

    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDAOImpl() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void add(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e", clazz).getResultList();
    }

    @Override
    public List<T> getAllWithLimit(int limit) {
        return entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e", clazz).setMaxResults(limit).getResultList();
    }

    @Override
    public T getByFieldNameAndValue(String name, Object object){
        return entityManager.createQuery( "SELECT e FROM" + clazz.getName() + "WHERE e." + name + " = :param", clazz)
                .setParameter("param",object)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public T getById(Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(Long id) {
        T entity = getById(id);
        delete(entity);
    }
    public void deleteByID(Long id) {
        Query query = entityManager.createQuery("DELETE FROM" +clazz.getName() +" e WHERE e.id = :param");
        query.setParameter("param", id);
        int rowsDeleted = query.executeUpdate();

    }
}
