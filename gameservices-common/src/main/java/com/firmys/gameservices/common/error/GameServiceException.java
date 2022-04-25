package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.GameData;

// FIXME, make me useful for returning exceptions
public class GameServiceException extends RuntimeException {
    private GameServiceError gameServiceError;
    private String gameDataType;
    public static final Builder builder = new Builder();

    public GameServiceException(){}

    public GameServiceException(GameServiceError gameServiceError, String gameDataType) {
        this.gameServiceError = gameServiceError;
        this.gameDataType = gameDataType;
    }

    private GameServiceException(GameServiceException.Builder builder) {
        this.gameServiceError = builder.gameServiceError;
        this.gameDataType = builder.gameDataType;
    }

    public GameServiceError getGameServiceError() {
        return gameServiceError;
    }

    public String getGameDataType() {
        return gameDataType;
    }

    public static class Builder {
        private GameServiceError gameServiceError;
        private String gameDataType;

        public Builder withGameServiceError(GameServiceError gameServiceError) {
            this.gameServiceError = gameServiceError;
            return this;
        }

        public Builder withGameDataType(Class<? extends GameData> gameDataType) {
            this.gameDataType = gameDataType.getSimpleName();
            return this;
        }

        public GameServiceException build() {
            return new GameServiceException(this);
        }
    }

}
