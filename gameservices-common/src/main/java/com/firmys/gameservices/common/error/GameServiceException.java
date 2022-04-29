package com.firmys.gameservices.common.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.common.GameService;

import java.io.Serializable;

public class GameServiceException extends RuntimeException implements Serializable {

    private GameServiceError<? extends GameEntity, ? extends GameService<?>> gameServiceError;
    private String additionalDetails;

    public GameServiceException(GameServiceError<? extends GameEntity, ? extends GameService<?>> gameServiceError) {
        super(gameServiceError.toString());
        this.gameServiceError = gameServiceError;
    }

    public GameServiceException(Exception exception) {
        super(exception);
    }

    public GameServiceException(Exception exception, String... additionalDetails) {
        super(String.join("\n", additionalDetails), exception);
        this.additionalDetails = String.join("\n", additionalDetails);
    }

    public GameServiceError<? extends GameEntity, ? extends GameService<?>> getGameServiceError() {
        return gameServiceError;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    @JsonIgnore
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
