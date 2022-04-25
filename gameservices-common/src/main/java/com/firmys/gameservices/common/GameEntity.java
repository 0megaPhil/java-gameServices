package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;

import java.util.UUID;

public interface GameEntity extends GameData {
    UUID getUuid();
    int getId();
    void setError(GameServiceError error);
    GameServiceError getError();
}
