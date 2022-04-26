package com.firmys.gameservices.common.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.GameData;

import java.io.Serializable;
import java.util.Optional;

// FIXME, make me useful for returning exceptions
public class GameServiceException extends RuntimeException implements Serializable {
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

    public void setGameDataType(String gameDataType) {
        this.gameDataType = gameDataType;
    }

    public void setGameServiceError(GameServiceError gameServiceError) {
        this.gameServiceError = gameServiceError;
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public synchronized Throwable getCause() {
        return Optional.ofNullable(gameServiceError.getThrowable()).orElse(super.getCause());
    }

    @Override
    public String getMessage() {
        return gameServiceError.getMessage();
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        if(gameServiceError != null && gameServiceError.getThrowable() != null) {
            return gameServiceError.getThrowable().getStackTrace();
        }
        return new StackTraceElement[]{};
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
