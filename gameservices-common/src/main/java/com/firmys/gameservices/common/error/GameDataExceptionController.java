package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.GameEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GameDataExceptionController {

    static class ErrorEntity extends AbstractGameEntity {

        @Override
        public UUID getUuid() {
            return null;
        }

        @Override
        public int getId() {
            return 0;
        }
    }

//    @ExceptionHandler(GameServiceException.class)
//    public ResponseEntity<? extends GameServiceException> gameException(GameServiceException gameServiceException) {
//        return new ResponseEntity<>(gameServiceException, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(GameServiceException.class)
    public ResponseEntity<? extends GameEntity> gameException(GameServiceException gameServiceException) {
        ErrorEntity error = new ErrorEntity();
        error.setError(gameServiceException.getGameServiceError());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    /**
//     * Fail-over exception handler for services
//     * @param exception general exception
//     * @return response which includes GameServiceException
//     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<? extends GameServiceException> generalException(Exception exception) {
//        exception.printStackTrace();
//        return new ResponseEntity<>(GameServiceException.builder.withGameServiceError(
//                GameServiceError.builder.withName(exception.getClass().getSimpleName())
//                        .withDescription(exception.getMessage()).build()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends GameEntity> generalException(Exception exception) {
        ErrorEntity error = new ErrorEntity();
        error.setError(GameServiceError.builder.withName(exception.getClass().getSimpleName())
                .withDescription(exception.getMessage()).build());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
