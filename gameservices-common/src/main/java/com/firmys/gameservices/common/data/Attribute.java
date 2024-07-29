package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.CommonObject;

public interface Attribute<K, V> extends CommonObject {
    Attribute<K, V> withValue(V value);
    V value();

    Attribute<K, V> withKey(K key);
    K key();

    Attribute<K, V> withDescription(String description);
    String description();
}
