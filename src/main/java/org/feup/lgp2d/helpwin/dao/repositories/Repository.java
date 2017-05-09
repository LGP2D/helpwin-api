package org.feup.lgp2d.helpwin.dao.repositories;

import java.util.List;
import java.util.function.Predicate;

public interface Repository<T> {
    List<T> getAll();
    T getOne(int id);
    T create(T entity);
    void delete(int id) throws RuntimeException;
    void delete(T entity);
}
