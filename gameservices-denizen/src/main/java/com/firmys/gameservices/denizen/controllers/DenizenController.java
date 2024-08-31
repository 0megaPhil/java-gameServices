package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.DENIZEN_PATH;

import com.firmys.gameservices.denizen.services.DenizenService;
import com.firmys.gameservices.generated.models.Denizen;
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
@RequestMapping(DENIZEN_PATH)
@Accessors(chain = true, fluent = true)
public class DenizenController extends ServiceController<Denizen> {

  private final DenizenService service;

  @Override
  public Mono<Denizen> create(Denizen object) {
    return service.create(object);
  }
}
