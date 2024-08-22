package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.EffectRepository;
import com.firmys.gameservices.generated.models.Effect;
import com.firmys.gameservices.service.GameService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class EffectService extends GameService<Effect> {
  private final EffectRepository repository;

  private final Class<Effect> entityType = Effect.class;
}
