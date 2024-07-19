package com.firmys.gameservices.characters;

import com.firmys.gameservices.characters.models.Character;
import com.github.javafaker.Faker;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharacterTests {

  private final Faker faker = new Faker();

  @Test
  void createCharacter() {
    Character character = Character.builder().build();
    Assertions.assertThat(character).isNotNull();
    Assertions.assertThat(character.uuid()).isNotNull();
    character =
        character.toBuilder()
            .age(faker.number().numberBetween(0, Integer.MAX_VALUE))
            .name(faker.name().fullName())
            .gender(faker.witcher().school())
            .height(faker.number().numberBetween(12, 120))
            .weight(faker.number().numberBetween(0, Integer.MAX_VALUE))
            .description(faker.witcher().quote())
            .inventoryId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .build();
    Assertions.assertThat(character).isNotNull();
    Assertions.assertThat(character.uuid()).isNotNull();
  }
}
