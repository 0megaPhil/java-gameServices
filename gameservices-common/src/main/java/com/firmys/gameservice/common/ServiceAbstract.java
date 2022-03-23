package com.firmys.gameservice.common;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceAbstract<R> implements Service<R> {

    private final Repository<R> repository;
    private final String basePath;

    public ServiceAbstract(
            Repository<R> repository,
            String basePath) {
        this.repository = repository;
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public R save(R entity) {
        return repository.save(entity);
    }

    public Set<R> saveAll(Set<R> entities) {
        return StreamSupport.stream(repository.saveAll(entities).spliterator(), false)
                .collect(Collectors.toSet());
    }

    public R findById(int id) {
        return repository.findById(id).orElseThrow();
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    public Set<R> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    public Set<R> findAllById(Set<Integer> ids) {
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false)
                .collect(Collectors.toSet());
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public void delete(R entity) {
        repository.delete(entity);
    }

    public void deleteAllById(Set<Integer> ids) {
        repository.deleteAllById(ids);
    }

    public void deleteAll(Set<R> entities) {
        repository.deleteAll(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
