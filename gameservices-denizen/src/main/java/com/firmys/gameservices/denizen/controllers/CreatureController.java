package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.CREATURE_PATH;

import com.firmys.gameservices.denizen.services.CreatureService;
import com.firmys.gameservices.generated.models.Creature;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(CREATURE_PATH)
@Accessors(chain = true, fluent = true)
public class CreatureController extends ServiceController<Creature> {

  private final CreatureService service;

  @Override
  public Mono<Creature> create(Creature object) {
    return service.create(object);
  }
}
