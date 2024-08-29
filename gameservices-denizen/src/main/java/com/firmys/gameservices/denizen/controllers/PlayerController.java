package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.PLAYER_PATH;

import com.firmys.gameservices.denizen.services.PlayerService;
import com.firmys.gameservices.generated.models.Player;
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
@RequestMapping(PLAYER_PATH)
@Accessors(chain = true, fluent = true)
public class PlayerController extends ServiceController<Player> {

  private final PlayerService service;

  @Override
  public Mono<Player> create(Player object) {
    return service.create(object);
  }
}
