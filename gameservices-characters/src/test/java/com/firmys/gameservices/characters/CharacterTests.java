package com.firmys.gameservices.characters;

import com.firmys.gameservices.characters.models.Character;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharacterTests {
  @Test
  void createCharacter() {
    Character character = Character.builder().build();
    Assertions.assertThat(character).isNotNull();
  }
}
