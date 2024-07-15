package com.firmys.gameservices.common;

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

  public final Mono<E> create(Mono<E> object) {
    return create(Flux.from(object)).single();
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
