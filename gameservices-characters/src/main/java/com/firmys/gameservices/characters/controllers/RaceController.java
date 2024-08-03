package com.firmys.gameservices.characters.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.characters.models.Race;
import com.firmys.gameservices.characters.services.RaceService;
import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@RestController
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class RaceController extends CommonController<Race> {

  private static final String BASE_PATH = RACE_PATH;

  private final RaceService service;
  private final Class<Race> entityClass = Race.class;

  @GetMapping(BASE_PATH + PATH_UUID)
  public Mono<Race> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(BASE_PATH)
  public Flux<Race> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Race> create(@RequestBody Race object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Race> update(@RequestBody Race object) {
    return service.update(object);
  }

  @DeleteMapping(BASE_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
