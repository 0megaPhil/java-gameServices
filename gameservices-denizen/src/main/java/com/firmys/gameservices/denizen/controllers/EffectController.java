package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.EFFECT_PATH;

import com.firmys.gameservices.denizen.services.EffectService;
import com.firmys.gameservices.generated.models.Effect;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(EFFECT_PATH)
@Accessors(chain = true, fluent = true)
public class EffectController extends ServiceController<Effect> {
  private final EffectService service;
}
