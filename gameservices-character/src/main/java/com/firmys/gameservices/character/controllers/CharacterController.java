package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.CHARACTER_PATH;

import com.firmys.gameservices.character.services.CharacterService;
import com.firmys.gameservices.generated.models.Character;
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
@RequestMapping(CHARACTER_PATH)
@Accessors(chain = true, fluent = true)
public class CharacterController extends ServiceController<Character> {

  private final CharacterService service;

  @Override
  public Mono<Character> create(Character object) {
    return service.create(object);
  }
}
