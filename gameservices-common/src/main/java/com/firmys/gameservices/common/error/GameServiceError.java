package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.common.JsonUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;

@RestControllerAdvice
public class GameServiceError<D extends GameEntity> implements Serializable {

    private String message;
    private String request;

    public GameServiceError(String message,
                            D request) {
        this.message = message;
        this.request = JsonUtils.writeObjectAsString(request);
    }

    public GameServiceError(String message,
                            String request) {
        this.message = message;
        this.request = request;
    }

    public GameServiceError() {

    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GameServiceError{" +
                "message='" + message + '\'' +
                ", request='" + request + '\'' +
                '}';
    }
}
