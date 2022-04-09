package com.firmys.gameservice.common.error;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservice.common.GameEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GameServiceError implements Serializable, GameEntity {

    private final String name;
    private final String description;
    private final String message;
    private final Throwable throwable;
    @JsonIgnore
    private final Throwable fullThrowable;
    private final GameEntity request;
    private final Date date;
    public static final Builder builder = new Builder();

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

        public Builder withThrowable(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder withRequest(GameEntity request) {
            this.request = request;
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
