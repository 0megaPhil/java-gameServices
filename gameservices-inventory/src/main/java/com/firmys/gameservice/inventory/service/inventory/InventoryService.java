package com.firmys.gameservice.inventory.service.inventory;

import com.firmys.gameservice.common.ServicePaths;
import com.firmys.gameservice.common.GameService;
import com.firmys.gameservice.inventory.service.data.Inventory;
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
public class InventoryService implements GameService<Inventory> {
    public final String cacheName = ServicePaths.INVENTORY;
    public final String cacheManagerName = ServicePaths.INVENTORY + "CacheManager";

    @Autowired
    InventoryRepository repository;

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Inventory> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public Inventory save(Inventory entity) {
        return repository.save(entity);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void delete(Inventory entity) {
        repository.delete(entity);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Page<Inventory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public <S extends Inventory> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Inventory> findById(Integer integer) {
        return repository.findById(integer);
    }

    public boolean existsById(Integer integer) {
        return repository.existsById(integer);
    }

    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Inventory> findAll() {
        return repository.findAll();
    }

    public Iterable<Inventory> findAllById(Iterable<Integer> integers) {
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
    public void deleteAll(Iterable<? extends Inventory> entities) {
        repository.deleteAll(entities);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll() {
        repository.deleteAll();
    }

}
