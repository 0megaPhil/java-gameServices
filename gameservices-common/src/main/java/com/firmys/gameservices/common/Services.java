package com.firmys.gameservices.common;

import lombok.Getter;

@Getter
public enum Services {
  CHARACTER(CommonConstants.CHARACTER),
  INVENTORY(CommonConstants.INVENTORY),
  ITEM(CommonConstants.ITEM),
  TRANSACTION(CommonConstants.TRANSACTION),
  CURRENCY(CommonConstants.CURRENCY),
  USER(CommonConstants.USER),
  WORLD(CommonConstants.WORLD),
  CONFIG(CommonConstants.CONFIG),
  GATEWAY(CommonConstants.GATEWAY);

  private final String name;

  Services(String name) {
    this.name = name.toLowerCase();
  }
}
