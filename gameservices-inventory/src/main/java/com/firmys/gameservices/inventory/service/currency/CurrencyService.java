package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.common.GameService;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.inventory.service.data.Currency;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class CurrencyService implements GameService<Currency> {
    public final String cacheName = ServiceStrings.CURRENCY;
    public final String cacheManagerName = ServiceStrings.CURRENCY + "CacheManager";

    @Autowired
    CurrencyRepository repository;

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll(@NonNull Sort sort) {
        return repository.findAll(sort);
    }

    @NonNull
    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public Currency save(@NonNull Currency entity) {
        return repository.save(entity);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void delete(@NonNull Currency entity) {
        repository.delete(entity);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Page<Currency> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @NonNull
    @CachePut(value = cacheName, cacheManager = cacheManagerName)
    public <S extends Currency> Iterable<S> saveAll(@NonNull Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @NonNull
    public Optional<Currency> findById(@NonNull Integer integer) {
        return repository.findById(integer);
    }

    public boolean existsById(@NonNull Integer integer) {
        return repository.existsById(integer);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll() {
        return repository.findAll();
    }

    @NonNull
    public Iterable<Currency> findAllById(@NonNull Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    public long count() {
        return repository.count();
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteById(@NonNull Integer integer) {
        repository.deleteById(integer);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAllById(@NonNull Iterable<? extends Integer> integers) {
        repository.deleteAllById(integers);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll(@NonNull Iterable<? extends Currency> entities) {
        repository.deleteAll(entities);
    }

    @CacheEvict(value = cacheName, cacheManager = cacheManagerName)
    public void deleteAll() {
        repository.deleteAll();
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Optional<Currency> findOne(@NonNull Predicate predicate) {
        return repository.findOne(predicate);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll(@NonNull Predicate predicate) {
        return repository.findAll(predicate);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll(@NonNull Predicate predicate, @NonNull Sort sort) {
        return repository.findAll(predicate, sort);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll(@NonNull Predicate predicate, @NonNull OrderSpecifier<?>... orders) {
        return repository.findAll(predicate, orders);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Iterable<Currency> findAll(@NonNull OrderSpecifier<?>... orders) {
        return repository.findAll(orders);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public Page<Currency> findAll(@NonNull Predicate predicate, @NonNull Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public long count(@NonNull Predicate predicate) {
        return repository.count(predicate);
    }

    @Override
    public boolean exists(@NonNull Predicate predicate) {
        return repository.exists(predicate);
    }

    @NonNull
    @Cacheable(value = cacheName, cacheManager = cacheManagerName)
    public <S extends Currency, R> R findBy(
            @NonNull Predicate predicate,
            @NonNull Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return repository.findBy(predicate, queryFunction);
    }

}
