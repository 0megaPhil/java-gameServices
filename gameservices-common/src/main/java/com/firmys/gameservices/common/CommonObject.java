package com.firmys.gameservices.common;

public interface CommonObject {

  default String toJson() {
    return JsonUtils.toJson(this);
  }
}
