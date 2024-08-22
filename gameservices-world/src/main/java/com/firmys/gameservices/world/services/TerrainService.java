package com.firmys.gameservices.world.services;

import com.firmys.gameservices.generated.models.Terrain;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.world.data.TerrainRepository;
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
public class TerrainService extends GameService<Terrain> {
  private final TerrainRepository repository;
  private final GameServiceClient gameServiceClient;

  private final Class<Terrain> entityType = Terrain.class;
}
