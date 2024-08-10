package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.CommonConstants.LIMIT;
import static com.firmys.gameservices.common.CommonConstants.PATH_UUID;

import com.firmys.gameservices.generated.models.CommonEntity;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CommonController<E extends CommonEntity> {

  public abstract CommonService<E> service();

  public abstract Class<E> entityClass();

  @GetMapping(PATH_UUID)
  public Mono<E> get(@PathVariable UUID uuid) {
    return service().find(uuid);
  }

  @GetMapping
  public Flux<E> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service().find(limit);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> create(@RequestBody E object) {
    return service().create(Mono.just(object));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> update(@RequestBody E object) {
    return service().update(object);
  }

  @DeleteMapping
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service().delete(uuid);
  }
}
