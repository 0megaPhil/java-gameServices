package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.JsonUtils.JSON;

public interface CommonObject {

  static <C extends CommonObject> C fromJson(String json, Class<C> objectType) {
    return JSON.fromJson(json, objectType);
  }

  default String toJson() {
    return JSON.toJson(this);
  }
}
