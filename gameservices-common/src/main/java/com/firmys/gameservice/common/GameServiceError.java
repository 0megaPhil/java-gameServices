package com.firmys.gameservice.common;


import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestControllerAdvice
public class GameServiceError implements Serializable, GameData {

    private String name;
    private String description;
    private Throwable throwable;
    private GameData request;
    private final Date date = Date.from(Instant.now());

    public GameServiceError(GameData request, String name, String description, Throwable throwable) {
        this.request = request;
        this.name = name;
        this.description = description;
        this.throwable = throwable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    public GameData getRequest() {
        return request;
    }

    public void setRequest(GameData request) {
        this.request = request;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "GameServiceErrorHandler{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", throwable=" + throwable +
                ", request=" + request +
                ", date=" + date +
                '}';
    }

}
