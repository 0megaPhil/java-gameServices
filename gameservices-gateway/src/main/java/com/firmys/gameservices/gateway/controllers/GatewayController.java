package com.firmys.gameservices.gateway.controllers;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.CommonObject;
import com.firmys.gameservices.common.JsonUtils;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

  public static final String baseDataName = "error";
  public static final String gameServiceErrorBase = "/" + baseDataName;

  @GetMapping(gameServiceErrorBase)
  public CommonObject getError(ServerHttpRequest serverHttpRequest) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);

      public String getMessage() {
        return message;
      }
    };
  }

  @PostMapping(gameServiceErrorBase)
  public CommonObject postError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  @PutMapping(value = gameServiceErrorBase)
  public CommonObject putError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  @DeleteMapping(gameServiceErrorBase)
  public CommonObject deleteError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  private String serializeRequest(ServerHttpRequest serverHttpRequest) {
    String cookies = JsonUtils.toJson(serverHttpRequest.getCookies());
    return JsonUtils.toJson(
        Map.of(
            "Query Params",
            serverHttpRequest.getQueryParams(),
            "Id",
            serverHttpRequest.getId(),
            "Local Address",
            String.valueOf(Objects.requireNonNull(serverHttpRequest.getLocalAddress()).toString()),
            "Remote Address",
            String.valueOf(Objects.requireNonNull(serverHttpRequest.getRemoteAddress()).toString()),
            "Path",
            serverHttpRequest.getPath().toString(),
            "Cookies",
            cookies));
  }
}
