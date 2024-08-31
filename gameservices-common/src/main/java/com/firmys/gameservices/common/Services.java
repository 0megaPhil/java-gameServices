package com.firmys.gameservices.common;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Services {
  DENIZEN(CommonConstants.DENIZEN),
  ATTRIBUTE(CommonConstants.ATTRIBUTE),
  PROFESSION(CommonConstants.PROFESSION),
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
