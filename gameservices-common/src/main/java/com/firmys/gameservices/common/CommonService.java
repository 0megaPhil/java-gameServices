package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.generated.models.Options;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
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
@SuppressWarnings("unchecked")
public abstract class CommonService<E extends CommonEntity> {

  public abstract CommonRepository<E> repository();

  public abstract Class<E> entityType();

  public Mono<E> find(UUID uuid) {
    return repository().findById(uuid);
  }

  @Transactional
  public Flux<E> find(Flux<UUID> uuids) {
    return repository().findAllById(uuids);
  }

  @Transactional
  public Flux<E> find(Options options) {
    options = validOptions(options);
    return repository().findAll(Sort.by(options.sortBy())).take(options.limit());
  }

  private Options validOptions(Options options) {
    Options valid = Optional.ofNullable(options).orElse(Options.builder().build());
    return valid
        .withFilters(safeSet(valid.filters()))
        .withLimit(Optional.of(valid.limit()).filter(lim -> lim > 0).orElse(1000))
        .withSortBy(
            Optional.ofNullable(valid.sortBy()).filter(str -> !str.isBlank()).orElse("uuid"));
  }

  private Mono<E> findOneLike(E exampleObj) {
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher("name", ignoreCase())
            .withMatcher("uuid", exact())
            .withIgnoreCase();
    return repository()
        .findOne(Example.of(exampleObj, matcher))
        .doOnNext(obj -> log.info("Found existing {}", exampleObj.toJson()))
        .switchIfEmpty(Mono.just(exampleObj))
        .doOnError(th -> log.error(th.getMessage(), th));
  }

  @Transactional
  public Flux<E> find(UUID... uuids) {
    return repository().findAllById(Flux.fromArray(uuids));
  }

  @Transactional
  public Mono<E> create(Mono<E> object) {
    return object.flatMap(
        obj -> repository().save(prompt().apply(obj)).onErrorResume(th -> errorResponse(obj, th)));
  }

  private Mono<E> errorResponse(E object, Throwable throwable) {
    return (Mono<E>)
        Mono.just(
            object.withError(
                Error.builder()
                    .message(throwable.getMessage())
                    .name(throwable.getClass().getSimpleName())
                    .status(400)
                    .build()));
  }

  @Transactional
  public Mono<E> create(E object) {
    return repository().save(prompt().apply(object));
  }

  @Transactional
  public Flux<E> create(Flux<E> objects) {
    return repository().saveAll(objects.map(obj -> prompt().apply(obj)));
  }

  @Transactional
  public Mono<E> update(E object) {
    return repository().save(prompt().apply(object)).onErrorResume(th -> errorResponse(object, th));
  }

  @Transactional
  public Mono<E> update(Mono<E> object) {
    return object.flatMap(
        obj -> repository().save(prompt().apply(obj)).onErrorResume(th -> errorResponse(obj, th)));
  }

  @Transactional
  public Flux<E> update(Flux<E> objects) {
    return repository().saveAll(objects);
  }

  @Transactional
  public Mono<Void> delete(UUID uuid) {
    return delete(Flux.just(uuid)).single();
  }

  @Transactional
  public Mono<Void> delete(Flux<UUID> uuids) {
    return uuids.flatMap(repository()::deleteById).then();
  }

  @Transactional
  public Mono<E> findOrCreate(E object) {
    return findOneLike(object).switchIfEmpty(this.create(Mono.just(prompt().apply(object))));
  }

  @Transactional
  public Mono<E> updateOrCreate(E object) {
    return findOneLike(object)
        .filter(obj -> obj.uuid() != null)
        .flatMap(obj -> update(object))
        .switchIfEmpty(
            this.create(Mono.just(object)).onErrorResume(th -> errorResponse(object, th)));
  }

  @SuppressWarnings("unchecked")
  private Function<E, E> prompt() {
    return obj ->
        (E)
            obj.withPrompt(
                Optional.ofNullable(obj.prompt())
                    .orElseGet(
                        () ->
                            "Describe "
                                + obj.getClass().getSimpleName()
                                + "  based on these details "
                                + obj.toJson()));
  }
}
