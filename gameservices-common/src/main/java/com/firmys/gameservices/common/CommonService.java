package com.firmys.gameservices.common;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CommonService<E extends CommonEntity> {

  public abstract CommonRepository<E> repository();

  public Mono<E> find(UUID uuid) {
    return find(Flux.just(uuid)).single();
  }

  public Mono<E> find(Mono<UUID> uuid) {
    return find(Flux.from(uuid)).single();
  }

  public Flux<E> find(Flux<UUID> uuids) {
    return repository().findAllById(uuids);
  }

  public Flux<E> find(UUID... uuids) {
    return Optional.ofNullable(uuids)
        .filter(ids -> ids.length > 0)
        .map(ids -> find(Flux.fromStream(Arrays.stream(ids))))
        .orElseGet(() -> repository().findAll());
  }

  public final Mono<E> create(Mono<E> object) {
    return object.flatMap(obj -> repository().save(obj));
  }

  public final Flux<E> create(Flux<E> objects) {
    return repository().saveAll(objects);
  }

  public final Mono<E> update(Mono<E> object) {
    return update(Flux.from(object)).single();
  }

  public final Flux<E> update(Flux<E> objects) {
    return repository().saveAll(objects);
  }

  public final Mono<Void> delete(UUID uuid) {
    return delete(Flux.just(uuid)).single();
  }

  public final Mono<Void> delete(Mono<UUID> uuid) {
    return delete(Flux.from(uuid)).single();
  }

  public final Mono<Void> delete(Flux<UUID> uuids) {
    return uuids.flatMap(repository()::deleteById).then();
  }
}
