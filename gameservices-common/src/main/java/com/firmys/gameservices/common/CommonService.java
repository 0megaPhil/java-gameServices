package com.firmys.gameservices.common;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

import com.firmys.gameservices.generated.models.CommonEntity;
import jakarta.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The CommonService class is an abstract class that provides common CRUD operations for entities
 * extending CommonEntity. It defines abstract methods repository() that must be implemented by
 * subclasses to provide the corresponding repository to perform the operations.
 *
 * @param <E> The type of entity that extends CommonEntity
 */
@Slf4j
public abstract class CommonService<E extends CommonEntity> {

  public abstract CommonRepository<E> repository();

  public Mono<E> find(UUID uuid) {
    return find(Flux.just(uuid)).single();
  }

  public Flux<E> find(Flux<UUID> uuids) {
    return repository().findAllById(uuids);
  }

  public Mono<E> findOneLike(E exampleObj) {
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher("name", ignoreCase())
            .withIgnoreCase();
    return repository()
        .findOne(Example.of(exampleObj, matcher))
        .switchIfEmpty(Mono.just(exampleObj))
        .doOnError(th -> log.error(th.getMessage(), th));
  }

  public Flux<E> find(@Nullable Integer limit, UUID... uuids) {
    return Optional.ofNullable(uuids)
        .filter(ids -> ids.length > 0)
        .map(ids -> find(Flux.fromStream(Arrays.stream(ids))))
        .orElseGet(() -> repository().findAll())
        .take(Optional.ofNullable(limit).orElse(1000));
  }

  public Mono<E> create(Mono<E> object) {
    return object.flatMap(obj -> repository().save(obj));
  }

  public Flux<E> create(Flux<E> objects) {
    return repository().saveAll(objects);
  }

  public Mono<E> update(E object) {
    return update(Mono.just(object)).single();
  }

  public Mono<E> update(Mono<E> object) {
    return object.flatMap(obj -> repository().save(obj));
  }

  public Flux<E> update(Flux<E> objects) {
    return repository().saveAll(objects);
  }

  public Mono<Void> delete(UUID uuid) {
    return delete(Flux.just(uuid)).single();
  }

  public Mono<Void> delete(Flux<UUID> uuids) {
    return uuids.flatMap(repository()::deleteById).then();
  }

  public Mono<E> findOrCreate(E object) {
    return findOneLike(object).switchIfEmpty(this.create(Mono.just(object)));
  }

  public Mono<E> findBy(E object) {
    return findOneLike(object)
        .onErrorReturn(object)
        .doOnNext(obj -> log.info("{}: {}", obj.getClass().getSimpleName(), obj));
  }
}
