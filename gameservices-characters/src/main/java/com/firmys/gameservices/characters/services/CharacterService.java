package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.CommonService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterService extends CommonService<Character> {
  private final CharacterRepository repository;
}
