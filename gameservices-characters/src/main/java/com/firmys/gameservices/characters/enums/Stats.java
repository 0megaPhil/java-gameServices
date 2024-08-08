package com.firmys.gameservices.characters.enums;

import lombok.Getter;

@Getter
@lombok.experimental.Accessors(fluent = true)
public enum Stats {
  STRENGTH("Strength"),
  AGILITY("Agility"),
  DEXTERITY("Dexterity"),
  CHARISMA("Charisma"),
  INTELLIGENCE("Intelligence"),
  CREATIVITY("Creativity"),
  MEMORY("Memory"),
  WILL("Will"),
  PRESENCE("Presence"),
  DETERMINATION("Determination"),
  LUCK("Luck"),
  MAGNETISM("Magnetism"),
  WISDOM("Wisdom"),
  CONSTITUTION("Constitution");

  private final String description;

  Stats(String description) {
    this.description = description;
  }
}
