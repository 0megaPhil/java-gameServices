package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.character.services.StatService;
import com.firmys.gameservices.generated.models.Stat;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(STAT_PATH)
@Accessors(chain = true, fluent = true)
public class StatController extends ServiceController<Stat> {
  private final StatService service;
}
