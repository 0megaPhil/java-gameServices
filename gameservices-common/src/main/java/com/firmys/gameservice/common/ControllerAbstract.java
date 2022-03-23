package com.firmys.gameservice.common;

import java.util.Set;

public abstract class ControllerAbstract<R> implements Controller<R> {

    private final Service<R> service;
    private final String basePath;

    public ControllerAbstract(Service<R> service) {
        this.service = service;
        this.basePath = service.getBasePath();
    }


    public R save(R entity) {
        return service.save(entity);
    }

    public Set<R> saveAll(Set<R> entities) {
        return service.saveAll(entities);
    }

    public R findById(int id) {
        return service.findById(id);
    }

    public boolean existsById(int id) {
        return service.existsById(id);
    }

    public Set<R> findAll() {
        return service.findAll();
    }

    public Set<R> findAllById(Set<Integer> ids) {
        return service.findAllById(ids);
    }

    public long count() {
        return service.count();
    }

    public void deleteById(int id) {
        service.deleteById(id);
    }

    public void delete(R entity) {
        service.delete(entity);
    }

    public void deleteAllById(Set<Integer> ids) {
        service.deleteAllById(ids);
    }

    public void deleteAll(Set<R> entities) {
        service.deleteAll(entities);
    }

    public void deleteAll() {
        service.deleteAll();
    }

    public String getBasePath() {
        return basePath;
    }
}
