package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.JsonUtils.JSON;

public interface CommonObject {

  default String toJson() {
    return JSON.toJson(this);
  }
}
