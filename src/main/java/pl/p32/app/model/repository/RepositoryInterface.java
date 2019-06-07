package pl.p32.app.model.repository;

import java.util.List;

public interface RepositoryInterface<T> {

    void save(T t);
    T update(T t);
    void remove(T t);
    List<T> findAll();
}