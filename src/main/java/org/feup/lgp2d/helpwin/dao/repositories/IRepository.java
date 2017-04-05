package org.feup.lgp2d.helpwin.dao.repositories;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository<T> {
    T get(int id);
    T getSingleByPredicate(Predicate<T> predicate);
    List<T> getAll();
    List<T> get(Predicate<T> predicate);
    void create(T t);
    void delete(int id);
    void delete(T t);
    void delete(Predicate<T> predicate);
}
