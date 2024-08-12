package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.character.services.RaceService;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Race;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(RACE_PATH)
@Accessors(chain = true, fluent = true)
public class RaceController extends CommonController<Race> {

  private final RaceService service;
}
