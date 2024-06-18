package org.dtoexampleapp.service;

import java.util.List;

public interface ServiceOperation<T>{
    List<T> findAll();

    void deleteById(int id);

    void add(T dtoObject);

    void update(T dtoObject);
}
