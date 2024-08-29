package com.firmys.gameservices.world.controllers;

import static com.firmys.gameservices.common.CommonConstants.WORLD_PATH;

import com.firmys.gameservices.generated.models.World;
import com.firmys.gameservices.service.ServiceController;
import com.firmys.gameservices.world.services.WorldService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(WORLD_PATH)
@Accessors(chain = true, fluent = true)
public class WorldController extends ServiceController<World> {
  private final WorldService service;
}
