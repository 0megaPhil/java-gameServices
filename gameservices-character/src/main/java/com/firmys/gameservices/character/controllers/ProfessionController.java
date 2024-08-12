package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.character.services.ProfessionService;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Profession;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(PROFESSION_PATH)
@Accessors(chain = true, fluent = true)
public class ProfessionController extends CommonController<Profession> {
  private final ProfessionService service;
}
