package org.bootcamp.trashhunter.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository("abstractDAO")
public abstract class AbstractDAO<T>  {

    protected Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDAO() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void add(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public List<T> getAll() {
        return entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e", clazz).getResultList();
    }

    public List<T> getAllWithLimit(int limit) {
        return entityManager.createQuery("SELECT e FROM " + clazz.getName() + " e", clazz).setMaxResults(limit).getResultList();
    }

    public T getByFieldNameAndValue(String name, Object object){
        return entityManager.createQuery( "SELECT e FROM" + clazz.getName() + "WHERE e." + name + " = :param", clazz)
                .setParameter("param",object)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    public T getById(Long id) {
        return entityManager.find(clazz, id);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(Long id) {
        entityManager.createQuery("SELECT e FROM" + clazz.getName() + "WHERE e.id = :param", clazz).
    }

    public void deleteById(Long id) {
        T entity = getById(id);
        delete(entity);
    }
}
