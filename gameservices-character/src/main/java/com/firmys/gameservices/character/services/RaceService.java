package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.RaceRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.generated.models.Race;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class RaceService extends CommonService<Race> {
  private final RaceRepository repository;

  private final Class<Race> entityType = Race.class;
}
