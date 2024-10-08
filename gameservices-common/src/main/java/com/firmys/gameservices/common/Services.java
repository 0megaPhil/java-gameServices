package com.firmys.gameservices.common;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Services {
  PLAYER(CommonConstants.PLAYER),
  NPC(CommonConstants.NPC),
  CREATURE(CommonConstants.CREATURE),
  SKILL(CommonConstants.SKILL),
  STAT(CommonConstants.STAT),
  RACE(CommonConstants.RACE),
  PROFESSION(CommonConstants.RACE),
  FLAVOR(CommonConstants.FLAVOR),
  INVENTORY(CommonConstants.INVENTORY),
  ITEM(CommonConstants.ITEM),
  TRANSACTION(CommonConstants.TRANSACTION),
  CURRENCY(CommonConstants.CURRENCY),
  USER(CommonConstants.USER),
  WORLD(CommonConstants.WORLD),
  CONFIG(CommonConstants.CONFIG),
  GATEWAY(CommonConstants.GATEWAY);

  private final String baseUri;

  Services(String baseUri) {
    this.baseUri = baseUri;
  }

  public static Services byEntityName(String entityName) {
    return Arrays.stream(Services.values())
        .filter(entry -> entry.baseUri.equalsIgnoreCase(entityName))
        .findFirst()
        .orElseThrow();
  }
}
