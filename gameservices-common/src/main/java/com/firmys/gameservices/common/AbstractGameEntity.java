package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;

public abstract class AbstractGameEntity implements GameEntity {
    private GameServiceError error;

    @Override
    public void setError(GameServiceError error) {
        this.error = error;
    }

    @Override
    public GameServiceError getError() {
        return this.error;
    }
}
