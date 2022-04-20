package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.common.GameService;
import com.firmys.gameservices.inventory.service.data.Item;
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
public class ItemService implements GameService<Item> {
    public final String cacheName = ServicePaths.ITEM;
    public final String cacheManagerName = ServicePaths.ITEM + "CacheManager";

    @Autowired
    ItemRepository repository;

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Item> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public Item save(Item entity) {
        return repository.save(entity);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void delete(Item entity) {
        repository.delete(entity);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Page<Item> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public <S extends Item> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Item> findById(Integer integer) {
        return repository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return repository.existsById(integer);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Item> findAll() {
        return repository.findAll();
    }

    public Iterable<Item> findAllById(Iterable<Integer> integers) {
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
    public void deleteAll(Iterable<? extends Item> entities) {
        repository.deleteAll(entities);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll() {
        repository.deleteAll();
    }

}
