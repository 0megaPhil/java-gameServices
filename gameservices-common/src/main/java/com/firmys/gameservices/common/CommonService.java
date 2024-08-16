package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.generated.models.Options;
import java.util.Optional;
import java.util.function.Function;
import javax.sql.rowset.serial.SerialException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

  public static <E1 extends CommonEntity> Example<E1> nameExample(E1 exampleObj) {
    ExampleMatcher matcher =
        ExampleMatcher.matchingAny().withIgnoreNullValues().withMatcher("name", exact());
    return Example.of(exampleObj, matcher);
  }

  public abstract CommonRepository<E> repository();

  public abstract Class<E> entityType();

  public Mono<E> find(ObjectId id) {
    return repository().findById(id);
  }

  public Flux<E> find(Options options) {
    options = validOptions(options);
    return repository().findAll(Sort.by(options.sortBy())).take(options.limit());
  }

  private Options validOptions(Options options) {
    Options valid = Optional.ofNullable(options).orElse(Options.builder().build());
    return valid
        .withFilters(safeSet(valid.filters()))
        .withLimit(Optional.of(valid.limit()).filter(lim -> lim > 0).orElse(1000))
        .withSortBy(Optional.ofNullable(valid.sortBy()).filter(str -> !str.isBlank()).orElse("id"));
  }

  public Mono<E> findOneLike(E exampleObj) {
    return repository()
        .findOne(nameExample(exampleObj))
        .doOnNext(obj -> log.info("Found existing {}", exampleObj.toJson()))
        .doOnError(th -> log.error(th.getMessage(), th));
  }

  public Flux<E> findAllLike(E exampleObj) {
    return repository()
        .findAll(nameExample(exampleObj))
        .filter(obj -> obj.id() != null)
        .doOnError(th -> log.error(th.getMessage(), th));
  }

  public Mono<E> ensureValues(Mono<E> character) {
    return character;
  }

  public Flux<E> find(ObjectId... ids) {
    return repository().findAllById(Flux.fromArray(ids));
  }

  public Mono<E> create(Mono<E> object) {
    return ensureValues(object)
        .filter(obj -> obj.id() == null)
        .switchIfEmpty(Mono.error(new SerialException("id should be null")))
        .flatMap(obj -> repository().save(prompt().apply(obj)))
        .doOnNext(o -> log.info("Created {}", o))
        .doOnError(th -> log.error(th.getMessage(), th))
        .onErrorResume(th -> object.flatMap(o -> errorResponse(o, th)));
  }

  private Mono<E> errorResponse(E object, Throwable throwable) {
    return (Mono<E>)
        Mono.just(
            object.withError(
                Error.builder()
                    .message(throwable.getMessage())
                    .name(throwable.getClass().getSimpleName())
                    .errorCode(400)
                    .build()));
  }

  public Mono<E> create(E object) {
    return create(Mono.just(object));
  }

  public Mono<E> update(E object) {
    return this.update(Mono.just(object));
  }

  public Mono<E> update(Mono<E> object) {
    return ensureValues(object)
        .filter(obj -> obj.id() != null)
        .switchIfEmpty(Mono.error(new SerialException("id should not be null")))
        .flatMap(obj -> repository().save(obj))
        .doOnNext(o -> log.info("Updated {}", o))
        .doOnError(th -> log.error(th.getMessage(), th))
        .onErrorResume(th -> object.flatMap(o -> errorResponse(o, th)));
  }

  public Mono<Void> delete(ObjectId id) {
    clearData();
    return repository().deleteById(id);
  }

  public void clearData() {
    repository()
        .findAll()
        .subscribeOn(Schedulers.parallel())
        .flatMap(obj -> repository().deleteById(obj.id()))
        .subscribe(v -> log.info("Deleted All"));
  }

  @SuppressWarnings("unchecked")
  public Function<E, E> prompt() {
    return obj ->
        (E)
            obj.withPrompt(
                Optional.ofNullable(obj.prompt())
                    .orElseGet(
                        () ->
                            "Describe "
                                + obj.getClass().getSimpleName()
                                + " based on these details "
                                + obj.toJson()));
  }
}
