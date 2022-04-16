package com.firmys.gameservice.common.error;

import com.firmys.gameservice.common.GameData;

public class GameServiceException extends RuntimeException {
    private final GameServiceError gameServiceError;
    private final String gameDataType;
    public static final Builder builder = new Builder();

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
