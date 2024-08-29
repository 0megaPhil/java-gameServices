package com.firmys.gameservices.world.services;

import com.firmys.gameservices.generated.models.World;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.world.data.WorldRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class WorldService extends GameService<World> {
  private final WorldRepository repository;
  private final GameServiceClient gameServiceClient;

  private final Class<World> entityType = World.class;
}
