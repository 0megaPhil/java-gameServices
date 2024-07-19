package com.firmys.gameservices.characters.controllers;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.characters.services.CharacterService;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.common.ServiceConstants;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@RestController
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterController extends CommonController<Character> {

  private final CharacterService service;
  private final Class<Character> entityClass = Character.class;

  @GetMapping(ServiceConstants.CHARACTERS_PATH)
  public Flux<Character> batchGet(
      @RequestParam(value = ServiceConstants.UUID, required = false) UUID... uuids) {
    return service.find(uuids);
  }

  @GetMapping(ServiceConstants.CHARACTER_PATH)
  public Mono<Character> get(
      @RequestParam(value = ServiceConstants.UUID, required = false) UUID uuid) {
    return service.find(uuid);
  }

  @PostMapping(
      value = ServiceConstants.CHARACTERS_PATH,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Character> batchCreate(@RequestBody Flux<Character> objects) {
    return service.create(objects);
  }

  @PostMapping(value = ServiceConstants.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Character> create(@RequestBody Character object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = ServiceConstants.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Character> batchUpdate(@RequestBody Flux<Character> objects) {
    return service.update(objects);
  }

  @PutMapping(value = ServiceConstants.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Character> update(@RequestBody Mono<Character> object) {
    return service.update(object);
  }

  @DeleteMapping(ServiceConstants.CHARACTERS_PATH)
  public Mono<Void> batchDelete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.delete(uuids);
  }

  @DeleteMapping(ServiceConstants.CHARACTER_PATH)
  public Mono<Void> delete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Mono<UUID> uuid) {
    return service.delete(uuid);
  }
}
