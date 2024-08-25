package com.firmys.gameservices.service;

import static com.firmys.gameservices.common.FunctionUtils.orVoid;
import static com.firmys.gameservices.generated.models.Operations.CREATE_ONE;
import static com.firmys.gameservices.generated.models.Operations.DELETE_BY_ID;
import static com.firmys.gameservices.generated.models.Operations.FIND_ALL_LIKE;
import static com.firmys.gameservices.generated.models.Operations.FIND_BY_ID;
import static com.firmys.gameservices.generated.models.Operations.FIND_ONE_LIKE;
import static com.firmys.gameservices.generated.models.Operations.UPDATE_ONE;
import static com.firmys.gameservices.service.error.ErrorUtils.mongoDbError;
import static com.firmys.gameservices.service.error.ErrorUtils.toException;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import com.firmys.gameservices.common.FunctionUtils;
import com.firmys.gameservices.data.CommonRepository;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.generated.models.Operations;
import com.firmys.gameservices.generated.models.Options;
import com.firmys.gameservices.service.error.ServiceException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
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
public abstract class GameService<E extends CommonEntity> {

  public static <E1 extends CommonEntity> Example<E1> nameExample(E1 exampleObj) {
    ExampleMatcher matcher =
        ExampleMatcher.matchingAny().withIgnoreNullValues().withMatcher("name", exact());
    return Example.of(exampleObj, matcher);
  }

  public abstract GameServiceClient gameServiceClient();

  public abstract CommonRepository<E> repository();

  public abstract Class<E> entityType();

  // TODO: Finish call to FlavorService
  public Mono<E> flavor(ObjectId id) {
    return Mono.defer(() -> repository().findById(id));
  }

  public Mono<E> get(ObjectId id) {
    return findByIdChain().apply(id);
  }

  public Flux<E> find(Options options) {
    return findByOptionsChain(validOptions(options));
  }

  private Flux<E> findByOptionsChain(Options options) {
    return repository()
        .findAll(Sort.by(options.sortBy()))
        .take(options.limit())
        .doOnNext(o -> log.info("{} {}", FIND_ONE_LIKE.name(), o))
        .doOnError(th -> log.error(th.getMessage(), th))
        .onErrorMap(th -> toException(mongoDbError(entityType(), options).apply(th, FIND_ONE_LIKE)))
        .map(obj -> obj);
  }

  private Options validOptions(Options options) {
    Options valid = Optional.ofNullable(options).orElse(Options.builder().build());
    return valid
        .withFilters(FunctionUtils.safeSet(valid.filters()))
        .withLimit(Optional.of(valid.limit()).filter(lim -> lim > 0).orElse(1000))
        .withSortBy(Optional.ofNullable(valid.sortBy()).filter(str -> !str.isBlank()).orElse("id"));
  }

  public Flux<E> findAllLike(E exampleObj) {
    return repository()
        .findAll(nameExample(exampleObj))
        .doOnNext(obj -> log.info("Found multiple including {}", exampleObj.toJson()))
        .onErrorMap(
            th -> toException(mongoDbError(entityType(), exampleObj).apply(th, FIND_ALL_LIKE)));
  }

  public Mono<E> ensureValues(Mono<E> object) {
    return object;
  }

  public Mono<E> create(Mono<E> object) {
    return saveOneChain("CREATE", o -> o.id() == null, "id should be null")
        .apply(ensureValues(object));
  }

  private BiConsumer<E, SynchronousSink<Object>> errorConsumer(
      Predicate<E> predicate, String operation, String errMessage, int errorCode) {
    return (obj, sink) -> {
      if (predicate.test(obj)) {
        sink.next(obj);
      } else {
        sink.error(exception(operation, errMessage, errorCode, obj));
      }
    };
  }

  private ServiceException exception(String name, String message, int errorCode, Object data) {
    return new ServiceException(
        Error.builder()
            .title(name)
            .message(message)
            .data(orVoid(() -> String.valueOf(data)))
            .errorCode(errorCode)
            .build());
  }

  private Function<Mono<E>, Mono<E>> saveOneChain(
      String operation, Predicate<E> predicate, String message) {
    return mono ->
        mono.handle(errorConsumer(predicate, operation, message, 400))
            .flatMap(obj -> saveOrHandle((E) obj))
            .doOnNext(this::updateFlavor)
            .doOnNext(o -> log.info("{} {}", operation, o))
            .doOnError(th -> log.error("{} {}", th.getClass().getSimpleName(), th.getMessage()));
  }

  private Mono<E> saveOrHandle(E obj) {
    return repository()
        .save(prompt().apply(obj))
        .onErrorMap(th -> toException(mongoDbError(entityType(), obj).apply(th, CREATE_ONE)));
  }

  private void updateFlavor(E object) {
    Mono.just(object)
        .filter(o -> o.id() != null)
        .filter(o -> o.error() == null)
        .flatMap(o -> gameServiceClient().flavor(o.id(), entityType()))
        .filter(o -> o.characteristics() != null)
        .filter(o -> o.error() == null)
        .flatMap(o -> repository().save(o))
        .doOnError(th -> log.error("{} {}", th.getClass().getSimpleName(), th.getMessage()))
        .subscribeOn(Schedulers.parallel())
        .subscribe(o -> log.info("Flavor Updated: {}", o.toJson()));
  }

  private Function<ObjectId, Mono<E>> findByIdChain() {
    return id ->
        repository()
            .findById(id)
            .handle(errorConsumer(Objects::nonNull, FIND_BY_ID.name(), "null id not allowed", 400))
            .doOnNext(o -> log.info("{} {}", FIND_BY_ID.name(), o))
            .doOnError(th -> log.error(th.getMessage(), th))
            .switchIfEmpty(Mono.error(exception("FIND", "not found", 400, String.valueOf(id))))
            .onErrorMap(th -> toException(mongoDbError(entityType(), id).apply(th, FIND_BY_ID)))
            .map(obj -> (E) obj);
  }

  public Mono<E> create(E object) {
    return create(Mono.just(object));
  }

  public Mono<E> update(E object) {
    return update(Mono.just(object));
  }

  public Mono<E> update(Mono<E> object) {
    return saveOneChain(Operations.UPDATE_ONE.name(), o -> o.id() != null, "id should not be null")
        .apply(
            object
                .filter(obj -> obj.id() != null)
                .switchIfEmpty(
                    object
                        .flatMap(
                            obj ->
                                ensureValues(
                                    findAllLike(obj)
                                        .singleOrEmpty()
                                        .map(
                                            found ->
                                                (E)
                                                    obj.withId(found.id())
                                                        .withVersion(found.version()))))
                        .switchIfEmpty(
                            Mono.error(
                                toException(
                                    mongoDbError(entityType(), object)
                                        .apply(
                                            new RuntimeException(
                                                "no Id provided, and no LIKE found"),
                                            UPDATE_ONE))))));
  }

  public Mono<Boolean> delete(ObjectId id) {
    return deleteByIdChain().apply(id).then(Mono.just(true)).onErrorReturn(false);
  }

  private Function<ObjectId, Mono<Void>> deleteByIdChain() {
    return id ->
        repository()
            .deleteById(id)
            .doOnError(th -> log.error(th.getMessage(), th))
            .onErrorMap(th -> toException(mongoDbError(entityType(), id).apply(th, DELETE_BY_ID)));
  }

  // TODO - Remove after implementation
  public Mono<Void> clearAll() {
    return clearData();
  }

  private Mono<Void> clearData() {
    return repository()
        .findAll()
        .subscribeOn(Schedulers.parallel())
        .flatMap(obj -> repository().deleteById(obj.id()).then(Mono.just(obj.id())))
        .collectList()
        .doOnNext(list -> log.info("Deleted {}", list))
        .then();
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
