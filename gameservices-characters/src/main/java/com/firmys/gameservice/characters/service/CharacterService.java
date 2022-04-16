package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.ServicePaths;
import com.firmys.gameservice.common.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CharacterService implements GameService<Character> {
    public final String cacheName = ServicePaths.CHARACTER;
    public final String cacheManagerName = ServicePaths.CHARACTER + ServicePaths.CACHE_MANAGER_SUFFIX;

    @Autowired
    CharacterRepository repository;

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Character> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public Character save(Character character) {
        return repository.save(character);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void delete(Character entity) {
        repository.delete(entity);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Page<Character> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public <S extends Character> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Character> findById(Integer integer) {
        return repository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return repository.existsById(integer);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Character> findAll() {
        return repository.findAll();
    }

    public Iterable<Character> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    public long count() {
        return repository.count();
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAllById(Iterable<? extends Integer> integers) {
        repository.deleteAllById(integers);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll(Iterable<? extends Character> entities) {
        repository.deleteAll(entities);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll() {
        repository.deleteAll();
    }

}
