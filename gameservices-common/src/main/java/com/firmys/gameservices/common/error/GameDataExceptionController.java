package com.firmys.gameservices.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GameDataExceptionController {

    @ExceptionHandler(GameServiceException.class)
    public ResponseEntity<? extends GameServiceException> gameException(GameServiceException gameServiceException) {
        return new ResponseEntity<>(gameServiceException, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fail-over exception handler for services
     * @param exception general exception
     * @return response which includes GameServiceException
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends GameServiceException> generalException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(GameServiceException.builder.withGameServiceError(
                GameServiceError.builder.withName(exception.getClass().getSimpleName())
                        .withDescription(exception.getMessage()).withThrowable(exception).build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
