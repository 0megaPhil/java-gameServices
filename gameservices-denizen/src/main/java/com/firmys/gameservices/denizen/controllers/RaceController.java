package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.denizen.services.RaceService;
import com.firmys.gameservices.generated.models.Race;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(RACE_PATH)
@Accessors(chain = true, fluent = true)
public class RaceController extends ServiceController<Race> {

  private final RaceService service;
}
