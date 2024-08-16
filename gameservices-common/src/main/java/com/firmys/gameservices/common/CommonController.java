package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.CommonConstants.PATH_ID;
import static com.firmys.gameservices.common.FunctionUtils.orVoid;
import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.firmys.gameservices.generated.models.CommonEntity;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class CommonController<E extends CommonEntity> {

  public abstract CommonService<E> service();

  @GetMapping(PATH_ID)
  public Mono<E> get(@PathVariable ObjectId id) {
    return service().find(id);
  }

  @GetMapping
  public Flux<E> get(@RequestParam Map<String, String> params) {
    return Optional.ofNullable(params)
        .filter(p -> p.get(CommonConstants.ID) != null || p.get(CommonConstants.NAME) != null)
        .map(p -> orVoid(() -> JSON.convert(p, service().entityType())))
        .map(obj -> service().findAllLike(obj))
        .orElseGet(() -> service().find(JSON.options(params)));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> create(@RequestBody E object) {
    return service()
        .create(object)
        .doOnError(th -> log.error("{}", th.getClass().getSimpleName(), th));
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> update(@RequestBody E object) {
    return service()
        .update(object)
        .doOnError(th -> log.error("{}", th.getClass().getSimpleName(), th));
  }

  @DeleteMapping(PATH_ID)
  public Mono<Void> delete(@PathVariable ObjectId id) {
    return service().delete(id).doOnError(th -> log.error("{}", th.getClass().getSimpleName(), th));
  }
}
