package com.firmys.gameservices.gateway.controllers;

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
    public GameServiceError getError(
            ServerHttpRequest serverHttpRequest) {
        return GameServiceError.builder.withName(baseDataName)
                .withDescription(serializeRequest(serverHttpRequest)).withThrowable(null).build();
    }

    @PostMapping(gameServiceErrorBase)
    public GameServiceError postError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return GameServiceError.builder.withName(baseDataName)
                .withDescription(serializeRequest(serverHttpRequest)).withRequest(requestBody)
                .withThrowable(null).build();
    }

    @PutMapping(value = gameServiceErrorBase)
    public GameServiceError putError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return GameServiceError.builder.withName(baseDataName)
                .withDescription(serializeRequest(serverHttpRequest))
                .withRequest(requestBody).withThrowable(null).build();
    }

    @DeleteMapping(gameServiceErrorBase)
    public GameServiceError deleteError(
            ServerHttpRequest serverHttpRequest,
            @RequestBody(required = false) GameEntity requestBody) {
        return GameServiceError.builder.withName(baseDataName)
                .withDescription(serializeRequest(serverHttpRequest))
                .withRequest(requestBody).withThrowable(null).build();
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
