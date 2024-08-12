package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.EffectRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.generated.models.Effect;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class EffectService extends CommonService<Effect> {
  private final EffectRepository repository;

  private final Class<Effect> entityType = Effect.class;
}
