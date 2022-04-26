package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;

import java.io.Serializable;

public interface GameData extends Serializable {
    void setError(GameServiceError error);
    GameServiceError getError();
}
