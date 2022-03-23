package com.firmys.gameservice.common;

import org.springframework.web.client.RestTemplate;

import java.util.Set;

public abstract class ClientAbstract<E> implements Client<E> {

    private final Properties properties;
    private final RestTemplate template;
    private final String httpPath;
    private final Class<E> entityClass;

    public ClientAbstract(
            Properties properties,
            RestTemplate template,
            Class<E> entityClass) {
        this.properties = properties;
        this.template = template;
        this.entityClass = entityClass;

        this.httpPath = "http://" + properties.getHost() + ":" +
                properties.getPort() + properties.getUri();
    }

    public String getBasePath() {
        return httpPath;
    }

    public E save(E entity) {
        return post("", entity);
    }

    public E findById(int id) {
        return get(httpPath + "?id=" + id);
    }

    public Set<E> findAll() {
        return getAll(httpPath);
    }

    public boolean existsById(int id) {
        return false;
    }

    public void delete(E entity) {
        delete(httpPath, entity);
    }

    public void deleteById(int id) {
        delete(httpPath + "?id=" + id);
    }

    private Set<E> getAll(String uri) {
        return template.getForObject(uri, Set.class);
    }

    private E get(String uri) {
        return template.getForObject(uri, entityClass);
    }

    private E post(String uri, E entity) {
        return (E) template.postForObject(uri, entity, entityClass);
    }

    private E patch(String uri, E entity) {
        return template.patchForObject(uri, entity, entityClass);
    }

    private void put(String uri, E entity) {
        template.put(uri, entity);
    }

    private void delete(String uri, E entity) {
        template.delete(uri, entity);
    }

    private void delete(String uri) {
        template.delete(uri);
    }




}
