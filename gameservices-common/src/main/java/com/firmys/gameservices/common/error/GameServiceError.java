package com.firmys.gameservices.common.error;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.GameEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestControllerAdvice
public class GameServiceError implements Serializable {

    private String name;
    private String description;
    private String message;
    private Throwable throwable;
    @JsonIgnore
    private Throwable fullThrowable;
    private GameEntity request;
    private Date date;
    public static final Builder builder = new Builder();

    public GameServiceError() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setFullThrowable(Throwable fullThrowable) {
        this.fullThrowable = fullThrowable;
    }

    public void setRequest(GameEntity request) {
        this.request = request;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // FIXME
    private GameServiceError(Builder builder) {
        this.request = builder.request;
        this.name = builder.name;
        this.description = builder.description;
        this.fullThrowable = builder.throwable;
        this.throwable = builder.throwable;
        this.message = Optional.ofNullable(builder.throwable).orElse(new RuntimeException()).getMessage();
        if(fullThrowable != null) {
            StackTraceElement[] elements = this.fullThrowable.getStackTrace();
            this.throwable.setStackTrace(Arrays.stream(elements).limit(2).toArray(StackTraceElement[]::new));
        }
        this.date = builder.date;
    }

    public static class Builder {
        private final Date date = Date.from(Instant.now());
        private String name;
        private String description;
        private Throwable throwable;
        private GameEntity request;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withRequest(GameEntity request) {
            this.request = request;
            return this;
        }

        public Builder withThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public GameServiceError build() {
            return new GameServiceError(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public GameEntity getRequest() {
        return request;
    }

    public Throwable getFullThrowable() {
        return fullThrowable;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public UUID getUuid() {
        return null;
    }

    public int getId() {
        return 0;
    }

    @Override
    public String toString() {
        return "GameServiceError{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", throwable=" + throwable +
                ", fullThrowable=" + fullThrowable +
                ", request=" + request +
                ", date=" + date +
                '}';
    }
}
