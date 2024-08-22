package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.RaceRepository;
import com.firmys.gameservices.generated.models.Race;
import com.firmys.gameservices.service.GameService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class RaceService extends GameService<Race> {
  private final RaceRepository repository;

  private final Class<Race> entityType = Race.class;
}
