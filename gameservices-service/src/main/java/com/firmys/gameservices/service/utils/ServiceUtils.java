package com.firmys.gameservices.service.utils;

import static com.firmys.gameservices.common.FunctionUtils.orVoid;

import com.firmys.gameservices.generated.models.Options;
import jakarta.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class ServiceUtils {
  public static Options options(@Nullable Map<String, String> queryParams) {
    queryParams = Optional.ofNullable(queryParams).orElse(Map.of());
    return Options.builder()
        .sortBy(Optional.ofNullable(queryParams.get("sort")).orElse("id"))
        .limit(
            Optional.ofNullable(queryParams.get("limit"))
                .map(lim -> orVoid(() -> Integer.valueOf(lim)))
                .orElse(1000))
        .build();
  }
}
