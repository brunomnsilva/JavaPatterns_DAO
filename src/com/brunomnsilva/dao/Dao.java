package com.brunomnsilva.dao;

import java.util.Collection;

/**
 * An abstract API that performs CRUD operations on objects of type T and unique key K
 *
 * @param <T>
 * @param <K>
 *
 * @author brunomnsilva
 */
public interface Dao<T, K> {
    T get(K key);
    Collection<T> getAll();
    void save(T instance) throws DaoException;
    void update(T instance) throws DaoException;
    T delete(K key);
    int count();
}
