package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.CommonConstants.PATH_UUID;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Options;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CommonController<E extends CommonEntity> {

  public abstract CommonService<E> service();

  @GetMapping(PATH_UUID)
  public Mono<E> get(@PathVariable UUID uuid) {
    return service().find(uuid);
  }

  @GetMapping
  public Flux<E> get(@RequestParam Options options) {
    return service().find(options);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> create(@RequestBody E object) {
    return service().create(object);
  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<E> update(@RequestBody E object) {
    return service().update(object);
  }

  @DeleteMapping(PATH_UUID)
  public Mono<Void> delete(@PathVariable UUID uuid) {
    return service().delete(uuid);
  }
}
