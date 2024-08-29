package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.NPC_PATH;

import com.firmys.gameservices.denizen.services.NPCService;
import com.firmys.gameservices.generated.models.NPC;
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
@RequestMapping(NPC_PATH)
@Accessors(chain = true, fluent = true)
public class NPCController extends ServiceController<NPC> {

  private final NPCService service;

  @Override
  public Mono<NPC> create(NPC object) {
    return service.create(object);
  }
}
