package org.bootcamp.trashhunter.dao.impl.abstraction;

import java.util.List;

public interface AbstractDao<T> {
    void add(T entity);

    void update(T entity);

    List<T> getAll();

    List<T> getAllWithLimit(int limit);

    T getByFieldNameAndValue(String name, Object object);

    T getById(Long id);

    void delete(T entity);

    void deleteById(Long id);
}
