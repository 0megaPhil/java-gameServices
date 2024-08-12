package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.ProfessionRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.generated.models.Profession;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class ProfessionService extends CommonService<Profession> {
  private final ProfessionRepository repository;

  private final Class<Profession> entityType = Profession.class;
}
