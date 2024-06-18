package org.dtoexampleapp.repository;

import java.util.List;

public interface CrudOperations<T> {
    List<T> findAll();

    void deleteById(int id);

    void add(T entity);

    void update(T entity);
}
