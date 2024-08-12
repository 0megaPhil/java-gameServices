package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.character.services.EffectService;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Effect;
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
public class EffectController extends CommonController<Effect> {
  private final EffectService service;
}
