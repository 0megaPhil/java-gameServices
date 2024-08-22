package com.firmys.gameservices.denizen.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.denizen.services.SkillService;
import com.firmys.gameservices.generated.models.Skill;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(SKILL_PATH)
@Accessors(chain = true, fluent = true)
public class SkillController extends ServiceController<Skill> {
  private final SkillService service;
}
