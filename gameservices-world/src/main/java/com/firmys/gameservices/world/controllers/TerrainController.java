package com.firmys.gameservices.world.controllers;

import static com.firmys.gameservices.common.CommonConstants.TERRAIN_PATH;

import com.firmys.gameservices.generated.models.Terrain;
import com.firmys.gameservices.service.ServiceController;
import com.firmys.gameservices.world.services.TerrainService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(TERRAIN_PATH)
@Accessors(chain = true, fluent = true)
public class TerrainController extends ServiceController<Terrain> {
  private final TerrainService service;
}
