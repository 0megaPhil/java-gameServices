package com.firmys.gameservices.gateway.controllers;

import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.JsonUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
public class GatewayController {
    public final static String baseDataName = "error";
    public final static String gameServiceErrorBase = "/" + baseDataName;

    @GetMapping(gameServiceErrorBase)
    public GameData getError(
            ServerHttpRequest serverHttpRequest) {
        return new GameData() {
            private final String message = serializeRequest(serverHttpRequest);
            public String getMessage() {
                return message;
            }
        };
    }

    @PostMapping(gameServiceErrorBase)
    public GameData postError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return new GameData() {
            private final String message = serializeRequest(serverHttpRequest);
            private final GameEntity entity = requestBody;
            public String getMessage() {
                return message;
            }
            public GameEntity getEntity() {
                return entity;
            }
        };
    }

    @PutMapping(value = gameServiceErrorBase)
    public GameData putError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return new GameData() {
            private final String message = serializeRequest(serverHttpRequest);
            private final GameEntity entity = requestBody;
            public String getMessage() {
                return message;
            }
            public GameEntity getEntity() {
                return entity;
            }
        };
    }

    @DeleteMapping(gameServiceErrorBase)
    public GameData deleteError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return new GameData() {
            private final String message = serializeRequest(serverHttpRequest);
            private final GameEntity entity = requestBody;
            public String getMessage() {
                return message;
            }
            public GameEntity getEntity() {
                return entity;
            }
        };
    }

    private String serializeRequest(ServerHttpRequest serverHttpRequest) {
        String cookies = JsonUtils.writeObjectAsString(serverHttpRequest.getCookies());
        return JsonUtils.writeObjectAsString(Map.of(
                "Query Params",
                serverHttpRequest.getQueryParams(),
                "Id",
                serverHttpRequest.getId(),
                "Local Address",
                Optional.ofNullable(Objects.requireNonNull(serverHttpRequest.getLocalAddress()).toString())
                        .orElse("null"),
                "Remote Address",
                Optional.ofNullable(Objects.requireNonNull(serverHttpRequest.getRemoteAddress()).toString())
                        .orElse("null"),
                "Path",
                serverHttpRequest.getPath().toString(),
                "Cookies",
                cookies));
    }
}
