package com.firmys.gameservices.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JsonUtils {

  @Getter
  @Accessors(fluent = true, chain = true)
  private static final ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();

  @SneakyThrows
  public static <T> String toJson(T object) {
    return mapper.writeValueAsString(object);
  }

  @SneakyThrows
  public static <T> T fromJson(String jsonStr, Class<T> objectType) {
    return mapper.readValue(jsonStr, objectType);
  }
}
