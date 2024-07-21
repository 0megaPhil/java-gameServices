package com.firmys.gameservices.characters.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.characters.services.CharacterService;
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
public class CharacterController extends CommonController<Character> {

  private final CharacterService service;
  private final Class<Character> entityClass = Character.class;

  @GetMapping(CHARACTER_PATH + PATH_UUID)
  public Mono<Character> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(CHARACTER_PATH)
  public Flux<Character> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Character> create(@RequestBody Character object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Character> update(@RequestBody Character object) {
    return service.update(object);
  }

  @DeleteMapping(CHARACTER_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
