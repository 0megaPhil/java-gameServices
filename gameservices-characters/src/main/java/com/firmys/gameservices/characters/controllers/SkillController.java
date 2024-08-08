package com.firmys.gameservices.characters.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.characters.services.SkillService;
import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Skill;
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
public class SkillController extends CommonController<Skill> {

  private static final String BASE_PATH = SKILL_PATH;

  private final SkillService service;
  private final Class<Skill> entityClass = Skill.class;

  @GetMapping(BASE_PATH + PATH_UUID)
  public Mono<Skill> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(BASE_PATH)
  public Flux<Skill> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Skill> create(@RequestBody Skill object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Skill> update(@RequestBody Skill object) {
    return service.update(object);
  }

  @DeleteMapping(BASE_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
