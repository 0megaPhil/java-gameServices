package com.firmys.gameservice.common;

import java.io.Serializable;

public interface Exchange<T> extends Serializable {
    T get();
    void set(T object);

}
