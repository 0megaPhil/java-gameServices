package com.firmys.gameservice.common;

import java.util.Set;

public interface Controller<R> {

    R save(R entity);
    Set<R> saveAll(Set<R> entities);
    R findById(int id);
    boolean existsById(int id);
    Set<R> findAll();
    Set<R> findAllById(Set<Integer> ids);
    long count();
    void deleteById(int id);
    void delete(R entity);
    void deleteAllById(Set<Integer> ids);
    void deleteAll(Set<R> entities);
    void deleteAll();

}
