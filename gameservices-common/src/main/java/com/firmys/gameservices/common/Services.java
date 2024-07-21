package com.firmys.gameservices.common;

import lombok.Getter;

@Getter
public enum Services {
  CHARACTER(CommonConstants.CHARACTER),
  INVENTORY(CommonConstants.INVENTORY),
  TRANSACTION(CommonConstants.TRANSACTION),
  USER(CommonConstants.USER),
  WORLD(CommonConstants.WORLD);

  private final String name;

  Services(String name) {
    this.name = name.toLowerCase();
  }
}
