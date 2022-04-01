package com.firmys.gameservice.common.error;

import com.firmys.gameservice.common.GameEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameDataExceptionController {

    @ExceptionHandler(GameServiceException.class)
    public ResponseEntity<? extends GameEntity> gameException(GameServiceException gameServiceException) {
        return new ResponseEntity<>(gameServiceException.getGameServiceError(), HttpStatus.MULTI_STATUS);
    }

    /**
     * Failover exception handler for controllers
     * @param exception general exception
     * @return response which includes GameServiceException
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends GameEntity> generalException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(GameServiceException.builder.withGameServiceError(
                GameServiceError.builder.withName(exception.getClass().getSimpleName())
                        .withDescription(exception.getMessage()).build()).build()
                .getGameServiceError(), HttpStatus.MULTI_STATUS);
    }
}
