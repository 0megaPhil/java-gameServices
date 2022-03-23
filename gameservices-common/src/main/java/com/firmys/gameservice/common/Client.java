package com.firmys.gameservice.common;

import java.util.Set;

public interface Client<E> {

    E save(E entity);
    E findById(int id);
    Set<E> findAll();
    boolean existsById(int id);
    void deleteById(int id);
    void delete(E entity);
}
