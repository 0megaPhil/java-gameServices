package com.firmys.gameservices.service;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.FunctionUtils;
import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.service.utils.ServiceUtils;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class ServiceController<E extends CommonEntity> {

  public abstract GameService<E> service();

  @GetMapping(CommonConstants.PATH_ID)
  public Mono<E> get(@PathVariable ObjectId id) {
    return service().get(id);
  }

  @GetMapping
  public Flux<E> get(@RequestParam Map<String, String> params) {
    return Optional.ofNullable(params)
        .filter(p -> p.get(CommonConstants.ID) != null || p.get(CommonConstants.NAME) != null)
        .map(p -> FunctionUtils.orVoid(() -> JsonUtils.JSON.convert(p, service().entityType())))
        .map(obj -> service().findAllLike(obj))
        .orElseGet(() -> service().find(ServiceUtils.options(params)))
        .map(ob -> ob);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> create(@RequestBody E object) {
    return service()
        .create(object)
        .doOnError(th -> log.error("{}: {}", th.getClass().getSimpleName(), th.getMessage()));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> update(@RequestBody E object) {
    return service()
        .update(object)
        .doOnError(th -> log.error("{}: {}", th.getClass().getSimpleName(), th.getMessage()));
  }

  @DeleteMapping(CommonConstants.PATH_ID)
  public Mono<Boolean> delete(@PathVariable ObjectId id) {
    return service()
        .delete(id)
        .doOnError(th -> log.error("{}: {}", th.getClass().getSimpleName(), th.getMessage()));
  }

  // TODO - Remove after implementation
  @DeleteMapping
  public Mono<Void> delete() {
    return service().clearAll();
  }
}
