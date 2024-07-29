package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.characters.enums.Stats;
import com.firmys.gameservices.common.data.Attribute;
import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
public record CharacterStat(Stats key, Integer value, String description)
    implements Attribute<Stats, Integer> {

  public static CharacterStat of(Stats key, Integer value) {
    return new CharacterStat(key, value, key.description());
  }
}
