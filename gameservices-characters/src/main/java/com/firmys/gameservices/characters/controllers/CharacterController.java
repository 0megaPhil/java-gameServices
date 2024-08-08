package com.firmys.gameservices.characters.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.characters.services.CharacterService;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Character;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(CHARACTER_PATH)
@Accessors(chain = true, fluent = true)
public class CharacterController extends CommonController<Character> {

  private final CharacterService service;
  private final Class<Character> entityClass = Character.class;
}
