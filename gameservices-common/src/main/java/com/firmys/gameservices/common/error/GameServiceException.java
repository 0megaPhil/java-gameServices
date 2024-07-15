package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.CommonEntity;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public class GameServiceException extends RuntimeException implements Serializable {

  private GameServiceError<?> gameServiceError;
  private String additionalDetails;

  public GameServiceException(
      Exception exception, GameServiceError<? extends CommonEntity> gameServiceError) {
    super(gameServiceError.toString(), exception);
    this.gameServiceError = gameServiceError;
  }

  public GameServiceException() {}

  public GameServiceException(Exception exception) {
    super(exception);
  }

  public GameServiceException(Exception exception, String... additionalDetails) {
    super(String.join("\n", additionalDetails), exception);
    this.additionalDetails = String.join("\n", additionalDetails);
  }

  public GameServiceError<?> getGameServiceError() {
    return gameServiceError;
  }

  public String getAdditionalDetails() {
    return Optional.ofNullable(additionalDetails).orElse("");
  }

  public String getMessage() {
    if (gameServiceError == null) {
      gameServiceError = new GameServiceError<>();
      gameServiceError.message("null pointer exception, no GameServiceError populated");
    }
    return Optional.ofNullable(super.getMessage()).orElse(gameServiceError.toString());
  }

  public synchronized Throwable getCause() {
    return null;
  }

  public StackTraceElement[] getStackTrace() {
    return Arrays.copyOfRange(super.getStackTrace(), 0, 3);
  }
}
