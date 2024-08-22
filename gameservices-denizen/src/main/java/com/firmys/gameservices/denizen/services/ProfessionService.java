package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.ProfessionRepository;
import com.firmys.gameservices.generated.models.Profession;
import com.firmys.gameservices.service.GameService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class ProfessionService extends GameService<Profession> {
  private final ProfessionRepository repository;

  private final Class<Profession> entityType = Profession.class;
}
