package com.firmys.gameservices.app.controllers;

import com.firmys.gameservices.app.services.FlavorService;
import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.data.Flavor;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FlavorController {

  private final FlavorService service;

  @QueryMapping
  public Mono<Flavor> characterFlavor(@Argument UUID uuid) {
    return service
        .flavor(uuid, Character.class)
        .doOnNext(fl -> log.info("Flavor: {} - {}", Character.class.getSimpleName(), fl));
  }
}
