package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.CommonObject;

public interface Attribute<N, V> extends CommonObject {
  Attribute<N, V> withValue(V value);

  V value();

  Attribute<N, V> withName(N name);

  N name();

  Attribute<N, V> withDescription(String description);

  String description();
}
