package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;

public class AbstractGameData implements GameData {
    private GameServiceError error;

    public AbstractGameData() {
    }

    @Override
    public void setError(GameServiceError error) {
        this.error = error;
    }

    @Override
    public GameServiceError getError() {
        return error;
    }
}
