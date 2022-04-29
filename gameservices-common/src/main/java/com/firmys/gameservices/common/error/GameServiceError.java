package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.common.GameService;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;

@RestControllerAdvice
public class GameServiceError<D extends GameEntity, S extends GameService<D>> implements Serializable {

    private final String message;
    private final S gameService;
    private final D request;
    private final Class<?> requestClass;
    private final Class<?> serviceClass;

    public GameServiceError(S gameService,
                            D request,
                            String message) {
        this.gameService = gameService;
        this.request = request;
        this.serviceClass = gameService.getClass();
        this.requestClass = request.getClass();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public S getGameService() {
        return gameService;
    }

    public D getRequest() {
        return request;
    }

    public Class<?> getRequestClass() {
        return requestClass;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }
}
